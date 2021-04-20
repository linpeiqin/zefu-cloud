package com.zefu.mq.excutor;


import com.zefu.business.api.RemoteProductService;
import com.zefu.common.base.biz.TopicBiz;
import com.zefu.common.base.constants.Constants;
import com.zefu.common.base.domain.dto.response.device.DevicePageResDto;
import com.zefu.common.base.domain.gateway.mq.PropertyReaderMessageBo;
import com.zefu.common.base.domain.message.DeviceDownMessage;
import com.zefu.common.base.enums.ErrorEnum;
import com.zefu.common.base.exception.GException;
import com.zefu.common.protocol.base.IBaseProtocol;
import com.zefu.common.protocol.service.IProtocolUtilService;
import com.zefu.mq.mqttclient.PubMqttClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class PropertyReaderExecutor {

    @Autowired
    PubMqttClient pubMqttClient;
    @Autowired
    RemoteProductService productService;
    @Autowired
    IProtocolUtilService protocolUtilService;
    /***
     * 具体线程
     */
    @Async(Constants.TASK.PROPERTY_READER_NAME)
    public void execute(PropertyReaderMessageBo msg) {
        try {
            this.sendMsg(msg.getDevicePageResDto(), msg.getDeviceDownMessage());
        } catch (Exception e) {
            log.warn("内部队列消费异常", e);

        }

    }
    /**
     * 发送请求到设备端
     */
    private void sendMsg(DevicePageResDto devicePageResDto, DeviceDownMessage deviceDownMessage) {
        IBaseProtocol baseProtocol = this.queryProtocolByProductCode(devicePageResDto.getProductCode());
        String topic = TopicBiz.buildPropertyGet(devicePageResDto.getDeviceCode(), deviceDownMessage.getProductCode());
        byte[] msg = baseProtocol.encode(topic, devicePageResDto.getDeviceCode(), deviceDownMessage);
        pubMqttClient.publish(topic, msg);
    }

    /** 根据产品编码查询协议实现 */
    private IBaseProtocol queryProtocolByProductCode(String productCode) {
        try {
            return protocolUtilService.queryProtocolInstanceByCode(productService.queryProtocolCodeByCode(productCode).getData());
        } catch (Exception e) {
            log.warn("", e);
            throw GException.genException(ErrorEnum.INNER_EXCEPTION);
        }
    }
}
