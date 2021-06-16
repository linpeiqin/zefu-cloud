package com.zefu.mq.mqttclient;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zefu.common.base.constants.Constants;
import com.zefu.common.base.domain.gateway.mq.DeviceActiveMqBo;
import com.zefu.common.base.domain.gateway.mq.DeviceUpMessageBo;
import com.zefu.common.base.enums.ErrorEnum;
import com.zefu.common.base.exception.GException;
import com.zefu.mq.producer.DeviceActiveMqProducer;
import com.zefu.mq.producer.DeviceReplyMessageProducer;
import com.zefu.mq.producer.DeviceUpMessageProducer;
import io.netty.handler.codec.mqtt.MqttQoS;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;


@Service
@Slf4j
public class PubMqttClient implements MqttCallback {
    private static final Logger logger = LoggerFactory.getLogger(PubMqttClient.class);
    private MqttClient mqttClient;
    @Value("${mqtt.client.url}")
    String mqttUrl;
    @Value("${mqtt.client.timeout:30}")
    int timeout;
    @Value("${mqtt.client.keepalive:30}")
    int keepAlive;
    private MqttConnectOptions options = new MqttConnectOptions();
    /**
     * 用于标记是否连接成功
     */
    private boolean isConnected = false;
    private int count = 1;

    public void init() {
        try {
            this.mqttClient = new MqttClient(mqttUrl, this.genClientId(), new MemoryPersistence());
            //this.options.setCleanSession(true);
            this.options.setAutomaticReconnect(true);
            this.options.setConnectionTimeout(timeout);
            this.options.setKeepAliveInterval(keepAlive);
            this.options.setCleanSession(false);
            try {
                this.mqttClient.connect(options);
                this.mqttClient.setCallback(this);
                this.isConnected = true;
            } catch (Exception e) {
                log.warn("连接MQTT服务器", e);
                this.isConnected = false;
                this.tryConnect();
            }
        } catch (Exception e) {
            log.warn("连接MQTT服务器", e);
            System.exit(-1);
        }
    }

    private final String genClientId() {
        String clientId = Constants.MQTT.SYS_CLIENT_TOPIC_PREFIX/* + UUID
                .randomUUID().toString().replaceAll("-", "")*/;
        return clientId;
    }

    /**
     * 用于处理第一次连接失败的情况
     */
    private void tryConnect() {
        int count = 1;
        while (!this.isConnected) {
            try {
                int sleepTime = (1000 * count) % (1000 * 60 * 10);
                Thread.sleep(sleepTime);
                log.info("连接[{}]断开，尝试重连第{}次", this.mqttUrl, count++);
                this.mqttClient.connect(this.options);
                this.isConnected = true;
            } catch (Exception e) {
                log.warn("首次重连异常");
            }

        }
    }

    /**
     * 发布，默认qos为1，非持久化
     *
     * @param topic
     * @param pushMessage
     */
    public void publish(String topic, byte[] pushMessage) {
        publish(MqttQoS.AT_LEAST_ONCE.value(), false, topic, pushMessage);
    }

    public void subscribe(String topic) {
        try {
            this.mqttClient.subscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发布
     *
     * @param qos
     * @param retained
     * @param topic
     * @param pushMessage
     */
    public void publish(int qos, boolean retained, String topic, String pushMessage) {
        this.publish(qos, retained, topic, pushMessage.getBytes());
    }

    public void publish(int qos, boolean retained, String topic, byte[] pushMessage) {
        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(retained);
        message.setPayload(pushMessage);
        MqttTopic mTopic = this.mqttClient.getTopic(topic);
        if (null == mTopic) {
            log.error("topic not exist:{}", topic);
        }
        MqttDeliveryToken token;
        try {
            token = mTopic.publish(message);
            token.waitForCompletion();
        } catch (MqttPersistenceException e) {
            log.warn("向主题[{}]发布消息异常{}", topic, e);
            throw GException.genException(ErrorEnum.INVALID_MQTT_USER);
        } catch (MqttException e) {
            log.warn("向主题[{}]发布消息异常{}", topic, e);
            throw GException.genException(ErrorEnum.INVALID_MQTT_USER);
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {
        isConnected = false;
        try {
            log.info("连接[{}]断开，尝试重连第{}次", this.mqttUrl, count++);
            this.mqttClient.connect(this.options);
            this.isConnected = true;
        } catch (Exception e) {
            log.warn("重连异常");
        }
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        // subscribe后得到的消息会执行到这里面
        try {
            String msg = new String(mqttMessage.getPayload());
            logger.warn("发布消息客户端{}接收消息主题 : {}   消息内容: {}", mqttClient.getServerURI(), topic, msg);
            this.sendMq(topic, mqttMessage);
        } catch (Exception e) {
            logger.warn("mqtt 订阅消息异常", e);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

    private void sendMq(String topic, MqttMessage message) {
        if (!isConnected){
            return;
        }
        if (topic.startsWith(Constants.MQTT.GLOBAL_UP_PREFIX)) {
            DeviceUpMessageBo deviceUpMessageBo = new DeviceUpMessageBo();
            deviceUpMessageBo.setTopic(topic);
            deviceUpMessageBo.setPacketId((long) message.getId());
            deviceUpMessageBo.setSourceMsg(message.getPayload());
            if (topic.endsWith(Constants.TOPIC.MSG_REPLY)
                    || topic.endsWith(Constants.TOPIC.PROP_GET_REPLY)
                    || topic.endsWith(Constants.TOPIC.UPGRADE_REPLY)) {
                DeviceReplyMessageProducer.send(deviceUpMessageBo);
            } else {
                DeviceUpMessageProducer.send(deviceUpMessageBo);
            }
        }
        if (topic.startsWith("$SYS/brokers/")) {
            //处理网关和直连设备在线状态
            String msg = new String(message.getPayload());
            JSONObject jsonObject = JSON.parseObject(msg);
            String clientId = String.valueOf(jsonObject.get("clientid"));
            String username = String.valueOf(jsonObject.get("username"));
            String sockport = String.valueOf(jsonObject.get("sockport"));
            String ipaddress = String.valueOf(jsonObject.get("ipaddress"));
            if (topic.endsWith("disconnected")) {
                this.sendActiveOfflineMQ(clientId);
                logger.warn("客户端已掉线：{}", clientId);
            } else {
                this.sendActiveOnlineMQ(clientId, username, sockport, ipaddress);
                logger.warn("客户端已上线：{}", clientId);
            }
        }

    }

    public static void sendActiveOnlineMQ(String clientId, String username, String sockport, String ipaddress) {
        DeviceActiveMqBo deviceActiveMqBo = new DeviceActiveMqBo();
        deviceActiveMqBo.setHost(ipaddress);
        deviceActiveMqBo.setActive(true);
        deviceActiveMqBo.setPort(Integer.valueOf(sockport));
        deviceActiveMqBo.setDeviceCode(clientId);
        deviceActiveMqBo.setTimestamp(new Date());
        DeviceActiveMqProducer.send(deviceActiveMqBo);
    }

    public static void sendActiveOfflineMQ(String clientId) {
        try {
            DeviceActiveMqBo deviceActiveMqBo = new DeviceActiveMqBo();
            deviceActiveMqBo.setActive(false);
            deviceActiveMqBo.setDeviceCode(clientId);
            DeviceActiveMqProducer.send(deviceActiveMqBo);
        } catch (Exception e) {
            logger.warn("发生设备下线消息异常{}", clientId, e);
        }
    }
}
