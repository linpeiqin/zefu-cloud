package com.zefu.mq.excutor;


import com.zefu.business.api.RemoteDeviceService;
import com.zefu.common.base.constants.Constants;
import com.zefu.common.base.domain.gateway.mq.DeviceActiveMqBo;
import com.zefu.mq.mqttclient.PubMqttClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MqttUpMessageExecutor {
    @Autowired
    PubMqttClient pubMqttClient;
    @Async(Constants.TASK.MQTT_UP_NAME)
    public void execute(){
        try {
            this.pubMqttClient.subscribe(Constants.MQTT.GLOBAL_UP_PREFIX+"#");
        } catch (Exception e) {
            log.warn("", e);
        }
    }

}
