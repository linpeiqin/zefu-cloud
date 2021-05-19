package com.zefu.mq.mqttclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zefu.common.base.biz.MqttUtil;
import com.zefu.common.base.constants.Constants;
import com.zefu.common.base.domain.gateway.mq.DeviceActiveMqBo;
import com.zefu.common.base.domain.gateway.mq.DeviceUpMessageBo;
import com.zefu.mq.producer.DeviceActiveMqProducer;
import com.zefu.mq.producer.DeviceReplyMessageProducer;
import com.zefu.mq.producer.DeviceUpMessageProducer;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Date;


public class PubMqttCallback implements MqttCallback {
    private static final Logger logger = LoggerFactory.getLogger(PubMqttCallback.class);
    private MqttClient client;
    private MqttConnectOptions options;
    private String urls;


    public PubMqttCallback(MqttClient client, MqttConnectOptions options, String urls) {
        this.client = client;
        this.options = options;
        this.urls = urls;
    }

    @Override
    public void connectionLost(Throwable cause) {
        // 连接丢失后，一般在这里面进行重连
        logger.info("mqtt 连接丢失", cause);
        int count = 1;
        // int sleepTime = 0;
        boolean willConnect = true;
        while (willConnect) {
            try {
                Thread.sleep(100);
                logger.info("连接[{}]断开，尝试重连第{}次", this.client.getServerURI(), count++);
                this.client.connect(this.options);
                logger.info("重连成功");
                willConnect = false;
            } catch (Exception e) {
                logger.warn("重连异常", e);
            }

        }

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        //System.out.println("deliveryComplete---------" + token.isComplete());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // subscribe后得到的消息会执行到这里面
        try {
            String msg = new String(message.getPayload());
            this.sendMq(topic,message);
            logger.warn("发布消息客户端{}接收消息主题 : {}   消息内容: {}", client.getServerURI(), topic, msg);
        } catch (Exception e) {
            logger.warn("mqtt 订阅消息异常", e);
        }

    }
    private void sendMq(String topic,MqttMessage message){
        if(topic.startsWith(Constants.MQTT.GLOBAL_UP_PREFIX)){
            DeviceUpMessageBo deviceUpMessageBo = new DeviceUpMessageBo();
            deviceUpMessageBo.setTopic(topic);
            deviceUpMessageBo.setPacketId((long)message.getId());
            deviceUpMessageBo.setSourceMsg(message.getPayload());
            if(topic.endsWith(Constants.TOPIC.MSG_REPLY)
                    || topic.endsWith(Constants.TOPIC.PROP_GET_REPLY)
                    || topic.endsWith(Constants.TOPIC.UPGRADE_REPLY)){
                DeviceReplyMessageProducer.send(deviceUpMessageBo);
            }else{
                DeviceUpMessageProducer.send(deviceUpMessageBo);
            }
        }
        if (topic.startsWith("$SYS/brokers/")){
            //处理网关和直连设备在线状态
            String msg = new String(message.getPayload());
            JSONObject jsonObject = JSON.parseObject(msg);
            String clientId = String.valueOf(jsonObject.get("clientid"));
            String username = String.valueOf(jsonObject.get("username"));
            String sockport = String.valueOf(jsonObject.get("sockport"));
            String ipaddress = String.valueOf(jsonObject.get("ipaddress"));
            if (topic.endsWith("disconnected")) {
                this.sendActiveOfflineMQ(clientId);
                logger.warn("客户端已掉线：{}",clientId);
            } else {
                this.sendActiveOnlineMQ(clientId,username,sockport,ipaddress);
                logger.warn("客户端已上线：{}",clientId);
            }
        }

    }

    public static void sendActiveOnlineMQ(String clientId, String username, String sockport, String ipaddress){
        DeviceActiveMqBo deviceActiveMqBo = new DeviceActiveMqBo();
        deviceActiveMqBo.setHost(ipaddress);
        deviceActiveMqBo.setActive(true);
        deviceActiveMqBo.setPort(Integer.valueOf(sockport));
        deviceActiveMqBo.setDeviceCode(clientId);
        deviceActiveMqBo.setTimestamp(new Date());
        DeviceActiveMqProducer.send(deviceActiveMqBo);
    }
    public static void sendActiveOfflineMQ(String clientId){
        try{
            DeviceActiveMqBo deviceActiveMqBo = new DeviceActiveMqBo();
            deviceActiveMqBo.setActive(false);
            deviceActiveMqBo.setDeviceCode(clientId);
            DeviceActiveMqProducer.send(deviceActiveMqBo);
        }catch (Exception e){
            logger.warn("发生设备下线消息异常{}", clientId, e);
        }
    }
}
