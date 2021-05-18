package com.zefu.mq.excutor;


import com.zefu.common.base.constants.Constants;
import com.zefu.mq.mqttclient.PubMqttClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MqttDeviceStatusExecutor {
    @Autowired
    PubMqttClient pubMqttClient;
    @Async(Constants.TASK.MQTT_DEVICE_STATUS_NAME)
    public void execute(){
        try {
            this.pubMqttClient.subscribe("$SYS/brokers/+/clients/#");
        } catch (Exception e) {
            log.warn("", e);
        }
    }

}
