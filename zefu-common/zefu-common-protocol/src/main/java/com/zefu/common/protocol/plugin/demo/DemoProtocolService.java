package com.zefu.common.protocol.plugin.demo;

import com.alibaba.fastjson.JSONObject;

import com.zefu.common.base.annotations.ProtocolAnnotation;
import com.zefu.common.base.biz.TopicBiz;
import com.zefu.common.base.constants.Constants;
import com.zefu.common.base.domain.message.DeviceDownMessage;
import com.zefu.common.base.domain.message.DeviceReportMessage;
import com.zefu.common.base.enums.DeviceReplyEnum;
import com.zefu.common.base.enums.ErrorEnum;
import com.zefu.common.base.exception.GException;
import com.zefu.common.base.metadata.ProductFuncTypeEnum;
import com.zefu.common.core.utils.bus.JSONProvider;
import com.zefu.common.protocol.base.IBaseProtocol;
import com.zefu.common.protocol.domain.demo.ReplyResultBo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Slf4j
@Data
@ProtocolAnnotation(name = "演示协议", protocolCode = "demo", desc = "演示协议")
@Component
public class DemoProtocolService implements IBaseProtocol {

    @Override
    public DeviceReportMessage decode(String topic, String deviceCode, byte[] payload) {
        try {
            DeviceReportMessage deviceMessage = new DeviceReportMessage<>();
            String topicDeviceCode = TopicBiz.parseDeviceCode(topic);
            if(!deviceCode.equals(topicDeviceCode)){
                log.warn("设备[{}]上报topic[{}]权限不足", deviceCode, topic);
                String extraMsg = String.format("设备[%s]上报topic[%s]权限不足", deviceCode, topic);
                throw GException.genException(ErrorEnum.ES_CREATE_EXCEPTION, extraMsg);
            }


            String msg = new String(payload, Constants.GLOBAL.CHARSET_GB2312);
            log.info("[{}]准备解析协议【{}】:{}", topic, payload);
            /**属性上报*/
            this.parsePropMessage(topic, payload, deviceMessage);
            /**事件上报*/
            this.parseEventMessage(topic, payload, deviceMessage);
            /**消息回执上报*/
            this.parseReplyMessage(topic, payload, deviceMessage);
            /**设备属性读取回执*/
            this.parsePropertyGetMessage(topic, payload, deviceMessage);
            this.parseSubOffline(topic, payload, deviceMessage);
            this.parseSubOnline(topic, payload, deviceMessage);
            return deviceMessage;
        } catch (Exception e) {
            log.warn("解析协议异常:{}", payload, e);
        }
        return null;
    }

    @Override
    public byte[] encode(String topic, String deviceCode, DeviceDownMessage deviceDownMessage) {
        try{
            String msg = JSONProvider.toJSONString(deviceDownMessage);
            return msg.getBytes(Constants.GLOBAL.CHARSET_GB2312);
        }catch (Exception e){
            log.warn("加密数据异常 topic:{} device:{} body:{}", topic, deviceCode, deviceDownMessage);
            return null;
        }
    }

    private void parsePropMessage(String topic, byte[] payload, DeviceReportMessage deviceMessage){
        try{
            ProductFuncTypeEnum funcTypeEnum = TopicBiz.parseFuncTypeEnum(topic);
            if(!topic.endsWith("prop")){
                return;
            }
            String msg = new String(payload, Constants.GLOBAL.CHARSET_GB2312);
            Map<String, Object> map = JSONProvider.parseObjectDefValue(msg, Map.class);
            deviceMessage.setValue(map);
        }catch (Exception e){
            log.warn("解析协议异常", e);
            String extraMsg = "解析主题:"+ topic +" 的上报消息异常";
            throw GException.genException(ErrorEnum.PARSE_MSG_EXCEPTION, extraMsg);
        }
    }

    private void parseEventMessage(String topic, byte[] payload, DeviceReportMessage deviceMessage) {
        try {
            ProductFuncTypeEnum funcTypeEnum = TopicBiz.parseFuncTypeEnum(topic);
            if (!topic.endsWith("event")) {
                return;
            }
            String msg = new String(payload, Constants.GLOBAL.CHARSET_GB2312);
            Map<String, Object> map = JSONProvider.parseObjectDefValue(msg, Map.class);
            deviceMessage.setValue(map);
        } catch (Exception e) {
            log.warn("解析协议异常", e);
            String extraMsg = "解析主题:" + topic + " 的上报消息异常";
            throw GException.genException(ErrorEnum.PARSE_MSG_EXCEPTION, extraMsg);
        }
    }

    private void parseReplyMessage(String topic, byte[] payload, DeviceReportMessage deviceMessage){
        try{
            ProductFuncTypeEnum funcTypeEnum = TopicBiz.parseFuncTypeEnum(topic);
            if(!topic.endsWith(Constants.TOPIC.MSG_REPLY)){
                return;
            }
            String msg = new String(payload, Constants.GLOBAL.CHARSET_GB2312);
            deviceMessage.setReplyMessage(msg);
            Map<String, Object> map = JSONProvider.parseObjectDefValue(msg, Map.class);
            deviceMessage.setMessageId((String)map.get("messageId"));
            ReplyResultBo replyResultBo = JSONProvider.parseJsonObject((JSONObject)map.get("result"), ReplyResultBo.class);
            if(200 == replyResultBo.getCode()){
                deviceMessage.setStatus(DeviceReplyEnum.SUCCESS);
            }else if(-1 == replyResultBo.getCode()){
                deviceMessage.setStatus(DeviceReplyEnum.UNKNOWN);
            }else{
                deviceMessage.setStatus(DeviceReplyEnum.FAIL);
            }
        }catch (Exception e){
            log.warn("解析协议异常", e);
            String extraMsg = "解析主题:"+ topic +" 的回执消息异常";
            throw GException.genException(ErrorEnum.PARSE_MSG_EXCEPTION, extraMsg);
        }
    }
    /**
     * 读取设备属性回执上报
     * */
    private void parsePropertyGetMessage(String topic, byte[] payload, DeviceReportMessage deviceMessage){
        try{
            if(!topic.endsWith(Constants.TOPIC.PROP_GET_REPLY)){
                return;
            }
            String msg = new String(payload, Constants.GLOBAL.CHARSET_GB2312);
            Map<String, Object> map = JSONProvider.parseObjectDefValue(msg, Map.class);
            deviceMessage.setMessageId((String)map.get("messageId"));
            deviceMessage.setValue((Map)map.get("value"));
        }catch (Exception e){
            log.warn("解析协议异常", e);
            String extraMsg = "解析主题:"+ topic +" 的回执消息异常";
            throw GException.genException(ErrorEnum.PARSE_MSG_EXCEPTION, extraMsg);
        }
    }

    /**
     * 网关子设备上线
     * */
    private void parseSubOnline(String topic, byte[] payload, DeviceReportMessage deviceMessage){
        try{
            if(!topic.endsWith("online")){
                return;
            }
            String msg = new String(payload, Constants.GLOBAL.CHARSET_GB2312);
            List<String> devices = JSONProvider.parseArrayObject(msg, String.class);
            deviceMessage.setDevices(devices);
        }catch (Exception e){
            log.warn("解析协议异常", e);
            String extraMsg = "解析主题:"+ topic +" 子设备上线解析异常";
            throw GException.genException(ErrorEnum.PARSE_MSG_EXCEPTION, extraMsg);
        }
    }
    private void parseSubOffline(String topic, byte[] payload, DeviceReportMessage deviceMessage){
        try{
            if(!topic.endsWith("online")){
                return;
            }
            String msg = new String(payload, Constants.GLOBAL.CHARSET_GB2312);
            List<String> devices = JSONProvider.parseArrayObject(msg, String.class);
            deviceMessage.setDevices(devices);
        }catch (Exception e){
            log.warn("解析协议异常", e);
            String extraMsg = "解析主题:"+ topic +" 子设备下线解析异常";
            throw GException.genException(ErrorEnum.PARSE_MSG_EXCEPTION, extraMsg);
        }
    }
}
