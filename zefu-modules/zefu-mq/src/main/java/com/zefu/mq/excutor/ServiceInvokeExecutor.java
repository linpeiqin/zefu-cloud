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
     * ????????????
     * ??????????????????????????????????????????????????????????????????redis?????????????????????????????????????????????es???????????????es????????????????????????mqtt??????????????????es???????????????redis???
     */
    @Async(Constants.TASK.SERVICE_INVOKE_NAME)
    public void execute(ServiceInvokeMessageBo msg) {

        try {
            remoteBusComService.todayTotalIncr();
            //???redis????????????????????????
            this.rebuildMsg(msg);
            //??????????????????????????????
            IBaseProtocol protocolService =   protocolUtilService.queryProtocolInstanceByCode(msg.getProtocolCode());
            /** ?????????????????????json????????????????????? */
            //??????????????????????????????es??????
            EsInsertDataBo dataBo = this.parseMsg(msg, msg.getCommand());
            //??????????????????mqtt??????
            byte[] pubMsg = protocolService.encode(msg.getTopic(), msg.getDeviceCode(), this.buildDownMessage(dataBo));
            //??????????????????es??????
            this.saveData2Es(dataBo);
            //mqtt????????????
            pubMqttClient.publish(msg.getTopic(), pubMsg);
            //?????????????????????????????????????????????redis
            this.setRtCache(msg.getFuncType(), msg.getDeviceCode(), msg.getCurrTime(), dataBo);
        } catch (Exception e) {
            log.warn("????????????????????????", e);
        }

    }
    private void saveData2Es(EsInsertDataBo dataBo){
        Boolean ret = remoteBusComService.lockService(dataBo.getEsMessage().getMessageId()).getData();
        if(ret){
            dataCenterService.saveData(dataBo);
        }
    }
    /** ?????????????????????????????? */
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

    /** ??????????????????????????? */
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
     * ???????????????????????????json??????????????????????????????????????????
     */
    private EsInsertDataBo parseMsg(ServiceInvokeMessageBo reqMsg, JSONObject json) {
        Map<String, Object> resultMap = new HashMap<>();
        Object finalResult = null;
        EsInsertDataBo dataBo = new EsInsertDataBo();
        try {
            ProductFuncItemResDto funcItemResDto =
                productFuncService.queryFunc(reqMsg.getProductCode(), reqMsg.getFuncType(), reqMsg.getIdentifier()).getData();

            /** ???????????????????????? */
            BaseAttrItemBo inputItem =
                JSONProvider.parseObjectDefValue(funcItemResDto.getInputParam(), BaseAttrItemBo.class);
            inputItem.setIdentifier(funcItemResDto.getIdentifier());
            /** ???????????????????????????????????????????????????????????????map */
            /** ??????????????????????????????????????????????????? */
            if (MetaType.STRUCT.getCode().equals(inputItem.getDataType())) {
                resultMap = this.parseStructMsg(reqMsg, inputItem, json);
                finalResult = resultMap;
            } else {
                // ???????????????????????????????????????
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
            log.warn("??????", e);
            return dataBo;
        }
        return dataBo;
    }

    private final Map<String, Object> parseSampleMsg(ServiceInvokeMessageBo reqMsg, BaseAttrItemBo inputItem,
        JSONObject json) {
        Map<String, Object> resultMap = new HashMap<>();
        if (!inputItem.getIdentifier().equals(reqMsg.getIdentifier())) {
            throw GException.genException(ErrorEnum.INVALID_PARAM, "????????????");
        }
        Object value = json.getObject(reqMsg.getIdentifier(), Object.class);
        Object retrieveVal = value;
        if (StringUtils.isEmpty(inputItem.getDataType())) {
            /** ???????????????????????????,??????????????????ES?????????String??? ???????????????????????? */
            retrieveVal = "/";
        } else if (MetaType.DATE.getCode().equals(inputItem.getDataType())) {
            retrieveVal = value;
        }
        resultMap.put(inputItem.getIdentifier(), retrieveVal);
        return resultMap;

    }

    /**
     * ???????????????????????????????????????
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
