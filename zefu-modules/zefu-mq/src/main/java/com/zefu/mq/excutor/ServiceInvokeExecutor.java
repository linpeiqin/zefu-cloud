package com.zefu.mq.excutor;

import com.alibaba.fastjson.JSONObject;


import com.zefu.business.api.RemoteBusComService;
import com.zefu.business.api.RemoteProductFuncService;
import com.zefu.business.api.RemoteProductService;
import com.zefu.common.base.biz.EsUtil;
import com.zefu.common.base.biz.RedisKeyUtil;
import com.zefu.common.base.biz.TopicBiz;
import com.zefu.common.base.constants.Constants;
import com.zefu.common.base.domain.bo.BaseAttrItemBo;
import com.zefu.common.base.domain.dto.request.ProductFuncItemResDto;
import com.zefu.common.base.domain.gateway.mq.ServiceInvokeMessageBo;
import com.zefu.common.base.domain.message.DeviceDownMessage;
import com.zefu.common.base.domain.storage.EsMessage;
import com.zefu.common.base.enums.ErrorEnum;
import com.zefu.common.base.exception.GException;
import com.zefu.common.base.metadata.EsInsertDataBo;
import com.zefu.common.base.metadata.MetaType;
import com.zefu.common.base.metadata.ProductFuncTypeEnum;
import com.zefu.common.core.utils.bus.JSONProvider;
import com.zefu.common.protocol.base.IBaseProtocol;
import com.zefu.common.protocol.service.IProtocolUtilService;
import com.zefu.common.redis.service.RedisService;
import com.zefu.common.storage.service.IDataCenterService;
import com.zefu.mq.mqttclient.PubMqttClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class ServiceInvokeExecutor {

    @Autowired
    IDataCenterService dataCenterService;
    @Autowired
    RemoteProductFuncService productFuncService;
    @Autowired
    RedisService cacheTemplate;
    @Autowired
    RemoteProductService productService;
    @Autowired
    IProtocolUtilService protocolUtilService;
    @Autowired
    PubMqttClient pubMqttClient;
    @Autowired
    RemoteBusComService remoteBusComService;


    /***
     * 具体线程
     * 此线程在线程池中构建，系统启动时执行，用于将redis中的【服务下发】消息发送组装成es消息，存入es库中，并且发送到mqtt中，然后再将es消息回写到redis中
     */
    @Async(Constants.TASK.SERVICE_INVOKE_NAME)
    public void execute(ServiceInvokeMessageBo msg) {

        try {
            remoteBusComService.todayTotalIncr();
            //从redis找到要下发的消息
            this.rebuildMsg(msg);
            //根据协议编码获取协议
            IBaseProtocol protocolService =   protocolUtilService.queryProtocolInstanceByCode(msg.getProtocolCode());
            /** 来自设备转化为json对象的消息实体 */
            //把获取到的消息转化为es对象
            EsInsertDataBo dataBo = this.parseMsg(msg, msg.getCommand());
            //把消息转化为mqtt消息
            byte[] pubMsg = protocolService.encode(msg.getTopic(), msg.getDeviceCode(), this.buildDownMessage(dataBo));
            //将消息存入到es库中
            this.saveData2Es(dataBo);
            //mqtt发布消息
            pubMqttClient.publish(msg.getTopic(), pubMsg);
            //最后把发布出去的消息再次存入到redis
            this.setRtCache(msg.getFuncType(), msg.getDeviceCode(), msg.getCurrTime(), dataBo);
        } catch (Exception e) {
            log.warn("内部队列消费异常", e);
        }

    }
    private void saveData2Es(EsInsertDataBo dataBo){
        Boolean ret = remoteBusComService.lockService(dataBo.getEsMessage().getMessageId()).getData();
        if(ret){
            dataCenterService.saveData(dataBo);
        }
    }
    /** 组装下发给设备的消息 */
    private final DeviceDownMessage buildDownMessage(EsInsertDataBo dataBo) {
        DeviceDownMessage deviceDownMessage = new DeviceDownMessage();
        deviceDownMessage.setMessageId(dataBo.getEsMessage().getMessageId());
        deviceDownMessage.setTimestamp(System.currentTimeMillis());
        deviceDownMessage.setBody(dataBo.getEsMessage().getRequest());
        deviceDownMessage.setIdentifier(dataBo.getIdentifier());
        deviceDownMessage.setDeviceCode(dataBo.getEsMessage().getDeviceCode());
        deviceDownMessage.setProductCode(dataBo.getEsMessage().getProductCode());
        return deviceDownMessage;
    }

    /** 把实时状态存入缓存 */
    private Boolean setRtCache(ProductFuncTypeEnum funcType, String deviceCode, Date arriveTime,
                               EsInsertDataBo dataBo) {
        String redisKey = RedisKeyUtil.buildRtCacheKey(deviceCode, funcType);
        cacheTemplate.addHashMap(redisKey, dataBo.getIdentifier(), JSONProvider.toJSONString(dataBo.getEsMessage()));
        return true;
    }

    private void rebuildMsg(ServiceInvokeMessageBo msg) {
        String topic = TopicBiz.buildServiceInvoke(msg.getDeviceCode(), msg.getProductCode());
        msg.setTopic(topic);
        msg.setFuncType(ProductFuncTypeEnum.SERVICE);
        msg.setProtocolCode(productService.queryProtocolCodeByCode(msg.getProductCode()).getData());
        return;
    }

    /**
     * 将协议解析后得到的json数据按照产品属性进行重新组装
     */
    private EsInsertDataBo parseMsg(ServiceInvokeMessageBo reqMsg, JSONObject json) {
        Map<String, Object> resultMap = new HashMap<>();
        Object finalResult = null;
        EsInsertDataBo dataBo = new EsInsertDataBo();
        try {
            ProductFuncItemResDto funcItemResDto =
                productFuncService.queryFunc(reqMsg.getProductCode(), reqMsg.getFuncType(), reqMsg.getIdentifier()).getData();

            /** 下发给设备的数据 */
            BaseAttrItemBo inputItem =
                JSONProvider.parseObjectDefValue(funcItemResDto.getInputParam(), BaseAttrItemBo.class);
            inputItem.setIdentifier(funcItemResDto.getIdentifier());
            /** 不是结构的话，这里就是简单类型，无法转换为map */
            /** 数据库中存储的改字段下的标识符列表 */
            if (MetaType.STRUCT.getCode().equals(inputItem.getDataType())) {
                resultMap = this.parseStructMsg(reqMsg, inputItem, json);
                finalResult = resultMap;
            } else {
                // 参数是简单数据或者没有参数
                resultMap = this.parseSampleMsg(reqMsg, inputItem, json);
                finalResult = resultMap.get(reqMsg.getIdentifier());
            }
            String indexName =
                EsUtil.buildDevIndex(reqMsg.getFuncType(), reqMsg.getProductCode(), reqMsg.getIdentifier());
            dataBo.setIndexName(indexName);
            EsMessage esMessage = new EsMessage();
            esMessage.setRequest(finalResult);
            esMessage.setDeviceCode(reqMsg.getDeviceCode());
            esMessage.setProductCode(reqMsg.getProductCode());
            esMessage.setMessageId(reqMsg.getMessageId());
            esMessage.setTimestamp(reqMsg.getCurrTime());
            dataBo.setIdentifier(reqMsg.getIdentifier());
            dataBo.setEsMessage(esMessage);
        } catch (Exception e) {
            log.warn("异常", e);
            return dataBo;
        }
        return dataBo;
    }

    private final Map<String, Object> parseSampleMsg(ServiceInvokeMessageBo reqMsg, BaseAttrItemBo inputItem,
        JSONObject json) {
        Map<String, Object> resultMap = new HashMap<>();
        if (!inputItem.getIdentifier().equals(reqMsg.getIdentifier())) {
            throw GException.genException(ErrorEnum.INVALID_PARAM, "指令非法");
        }
        Object value = json.getObject(reqMsg.getIdentifier(), Object.class);
        Object retrieveVal = value;
        if (StringUtils.isEmpty(inputItem.getDataType())) {
            /** 这种情况下是空指令,此情况下默认ES类型是String， 默认值是如下设置 */
            retrieveVal = "/";
        } else if (MetaType.DATE.getCode().equals(inputItem.getDataType())) {
            retrieveVal = value;
        }
        resultMap.put(inputItem.getIdentifier(), retrieveVal);
        return resultMap;

    }

    /**
     * 解析内容非结构体的下发指令
     */
    private final Map<String, Object> parseStructMsg(ServiceInvokeMessageBo reqMsg, BaseAttrItemBo inputItem,
        JSONObject json) {
        Map<String, Object> resultMap = new HashMap<>();

        Map<String, BaseAttrItemBo> attrMap = new HashMap<>();
        for (BaseAttrItemBo bo : inputItem.getData()) {
            attrMap.put(bo.getIdentifier(), bo);
        }
        Map<String, Object> dataMap =
            (Map)JSONProvider.parseJsonObject(json.getJSONObject(reqMsg.getIdentifier()), Map.class);
        dataMap.forEach((key, value) -> {
            Object retrieveVal = value;
            if (attrMap.containsKey(key)) {
                BaseAttrItemBo attrItemBo = attrMap.get(key);
                if (MetaType.DATE.getCode().equals(attrItemBo.getDataType())) {
                    retrieveVal = (Long)value;
                }
                resultMap.put(key, retrieveVal);
            }
        });
        return resultMap;
    }
}
