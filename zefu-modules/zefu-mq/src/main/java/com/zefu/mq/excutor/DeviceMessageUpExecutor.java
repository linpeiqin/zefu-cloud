package com.zefu.mq.excutor;


import com.zefu.business.api.RemoteBusComService;
import com.zefu.business.api.RemoteDeviceService;
import com.zefu.business.api.RemoteProductFuncService;
import com.zefu.business.api.RemoteProductService;
import com.zefu.common.base.biz.EsUtil;
import com.zefu.common.base.biz.RedisKeyUtil;
import com.zefu.common.base.biz.TopicBiz;
import com.zefu.common.base.constants.Constants;
import com.zefu.common.base.domain.dto.request.ProductFuncItemResDto;
import com.zefu.common.base.domain.dto.response.device.DevicePageResDto;
import com.zefu.common.base.domain.gateway.mq.DeviceUpMessageBo;
import com.zefu.common.base.domain.message.DeviceReportMessage;
import com.zefu.common.base.domain.storage.EsMessage;
import com.zefu.common.base.enums.BatchOpEnum;
import com.zefu.common.base.enums.ErrorEnum;
import com.zefu.common.base.exception.GException;
import com.zefu.common.base.metadata.EsInsertDataBo;
import com.zefu.common.base.metadata.MetaType;
import com.zefu.common.base.metadata.ProductFuncTypeEnum;
import com.zefu.common.core.utils.bus.DateUtil;
import com.zefu.common.core.utils.bus.JSONProvider;
import com.zefu.common.protocol.base.IBaseProtocol;
import com.zefu.common.protocol.service.IProtocolUtilService;
import com.zefu.common.redis.service.RedisService;
import com.zefu.common.storage.service.IDataCenterService;
import com.zefu.common.storage.service.IMessageReplyService;
import com.zefu.common.storage.entity.MessageReplyEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;


@Service
@Slf4j
public class DeviceMessageUpExecutor {
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
    IMessageReplyService messageReplyService;
    @Autowired
    RemoteBusComService remoteBusComService;
    @Autowired
    RemoteDeviceService deviceService;

    /***
     * 具体线程
     */
    @Async(Constants.TASK.DEVICE_UP_MESSAGE_NAME)
    public void execute(DeviceUpMessageBo msg) {
        try {
            remoteBusComService.todayTotalIncr();
            this.rebuildMsg(msg);
            if (msg.getProductCode() == null || msg.getDeviceCode() == null) return ;
            IBaseProtocol protocolService = this.queryProtocolByProductCode(msg.getProductCode());
            /** 来自设备转化为平台需要的数据结构 */
            DeviceReportMessage deviceMessage = protocolService.decode(msg.getTopic(), msg.getDeviceCode(), msg.getSourceMsg());
            deviceMessage.setProductCode(msg.getProductCode());
            deviceMessage.setDeviceCode(msg.getDeviceCode());
            this.processUpMessage(msg, deviceMessage);
            this.processReplyMessage(msg, deviceMessage);
            this.processPropertyGetReplyMessage(msg, deviceMessage);
            this.subDeviceActive(msg, deviceMessage);
        } catch (Exception e) {
            log.warn("内部队列消费异常", e);

        }

    }

    /**
     * 网关批量上报子设备状态
     */
    private final void subDeviceActive(DeviceUpMessageBo msg, DeviceReportMessage deviceMessage) {
        String topic = msg.getTopic();
        if (topic.endsWith("offline")) {
            deviceService.batchChangeStatusByCode(deviceMessage.getDevices(), BatchOpEnum.OFFLINE);
        } else if (topic.endsWith("online")) {
            deviceService.batchChangeStatusByCode(deviceMessage.getDevices(), BatchOpEnum.ONLINE);
        }
    }

    /** 服务调用消息回执 */
    private final void processReplyMessage(DeviceUpMessageBo msg, DeviceReportMessage deviceMessage) {
        String topic = msg.getTopic();
        if (!topic.endsWith(Constants.TOPIC.MSG_REPLY)) {
            return;
        }
        MessageReplyEntity messageReplyEntity = new MessageReplyEntity();
        messageReplyEntity.setMessageId(deviceMessage.getMessageId());
        messageReplyEntity.setBody(deviceMessage.getReplyMessage());
        messageReplyEntity.setDeviceCode(deviceMessage.getDeviceCode());
        messageReplyEntity.setDevTimestamp(deviceMessage.getDeviceTimestamp());
        messageReplyEntity.setProductCode(deviceMessage.getProductCode());
        messageReplyEntity.setTimestamp(msg.getCurrTime());
        messageReplyEntity.setStatus(deviceMessage.getStatus().getCode());
        messageReplyService.create(messageReplyEntity);
    }

    /** 处理设备主动上报的消息 */
    private final void processUpMessage(DeviceUpMessageBo msg, DeviceReportMessage deviceMessage) {
        String topic = msg.getTopic();
        if (!topic.endsWith("prop") && !topic.endsWith("event")) {
            return;
        }
        List<EsInsertDataBo> dataList = this.parseMsg(msg, deviceMessage);
        for (EsInsertDataBo item : dataList) {
            try {
                dataCenterService.saveData(item);
                this.setRtCache(msg.getFuncType(), msg.getDeviceCode(), msg.getCurrTime(), item);
            } catch (Exception e) {
                log.warn("", e);
            }
        }
    }

    /** 处理设备对属性读取的响应 */
    private final void processPropertyGetReplyMessage(DeviceUpMessageBo msg, DeviceReportMessage deviceMessage) {
        String topic = msg.getTopic();
        if (!topic.endsWith(Constants.TOPIC.PROP_GET_REPLY)) {
            return;
        }
        if (StringUtils.isEmpty(deviceMessage.getMessageId())) {
            log.warn("属性读取响应回执缺失messageId");
            return;
        }
        List<EsInsertDataBo> dataList = this.parseMsg(msg, deviceMessage);
        for (EsInsertDataBo item : dataList) {
            try {
                dataCenterService.saveData(item);
                this.setRtCache(msg.getFuncType(), msg.getDeviceCode(), msg.getCurrTime(), item);
            } catch (Exception e) {
                log.warn("", e);
            }
        }
        remoteBusComService.propGetValueWrite(deviceMessage.getMessageId(), JSONProvider.toJSONString(deviceMessage.getValue()));

    }

    private void rebuildMsg(DeviceUpMessageBo msg) {
        String deviceCode = TopicBiz.parseDeviceCode(msg.getTopic());
        DevicePageResDto devicePageResDto = deviceService.queryByDevCode(deviceCode).getData();
        if (devicePageResDto == null) return ;
        String funcType = TopicBiz.parseFuncType(msg.getTopic());
        ProductFuncTypeEnum funcTypeEnum = ProductFuncTypeEnum.explain(funcType);
        msg.setProductCode(devicePageResDto.getProductCode());
        msg.setDeviceCode(deviceCode);
        msg.setFuncType(funcTypeEnum);
        return;
    }

    /** 根据产品编码查询协议实现 */
    public IBaseProtocol queryProtocolByProductCode(String productCode) {
        try {
            return protocolUtilService.queryProtocolInstanceByCode(productService.queryProtocolCodeByCode(productCode).getData());
        } catch (Exception e) {
            log.warn("", e);
            throw GException.genException(ErrorEnum.INNER_EXCEPTION);
        }

    }

    /** 把实时状态存入缓存 */
    private Boolean setRtCache(ProductFuncTypeEnum funcType, String deviceCode, Date arriveTime,
        EsInsertDataBo dataBo) {
        String redisKey = RedisKeyUtil.buildRtCacheKey(deviceCode, funcType);
        cacheTemplate.setCacheMapValue(redisKey, dataBo.getIdentifier(), JSONProvider.toJSONString(dataBo.getEsMessage()));
        return true;
    }

    /**
     * 将协议解析后得到的json数据按照产品属性进行重新组装
     */
    private List<EsInsertDataBo> parseMsg(DeviceUpMessageBo reqMsg, DeviceReportMessage deviceMessage) {

        List<EsInsertDataBo> dataList = new ArrayList<>();

        try {
            List<ProductFuncItemResDto> funcList =
                productFuncService.listFuncByProductCode(reqMsg.getProductCode(), 1, reqMsg.getFuncType()).getData();
            /** 属性和类型对应的map映射关系，key是属性 value是类型 */
            Map<String, String> propsMap = new HashMap<>();
            for (ProductFuncItemResDto item : funcList) {
                propsMap.put(item.getIdentifier(), item.getDataType());
            }
            /** 设备传出过来的原始数据 */
            Map<String, Object> dataMap = deviceMessage.getValue();
            dataMap.forEach((key, value) -> {
                EsInsertDataBo esInsertDataBo = new EsInsertDataBo();
                EsMessage esMessage = new EsMessage();
                esMessage.setDeviceCode(deviceMessage.getDeviceCode());
                esMessage.setMessageId(deviceMessage.getMessageId());
                esMessage.setProductCode(deviceMessage.getProductCode());
                esMessage.setTimestamp(reqMsg.getCurrTime());
                esMessage.setDeviceTimestamp(deviceMessage.getDeviceTimestamp());

                String dataType = (String)propsMap.get(key);
                Object retrieveVal = value;
                if (propsMap.containsKey(key)) {
                    if (MetaType.DATE.getCode().equals(dataType)) {
                        Date date = new Date((Long)value);
                        retrieveVal = DateUtil.formatForEsGMT8(date);
                    }
                    esMessage.setRequest(retrieveVal);
                    String index = EsUtil.buildDevIndex(reqMsg.getFuncType(), deviceMessage.getProductCode(), key);
                    esInsertDataBo.setIndexName(index);
                    esInsertDataBo.setEsMessage(esMessage);
                    esInsertDataBo.setIdentifier(key);
                    dataList.add(esInsertDataBo);
                }
            });

        } catch (Exception e) {
            log.warn("", e);
            return dataList;
        }
        return dataList;
    }
}
