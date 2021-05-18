package com.zefu.mq.boot;


import com.zefu.mq.consume.*;
import com.zefu.mq.mqttclient.PubMqttClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GatewayBootService {
    @Autowired
    ActiveMqConsume activeMqConsume;
    @Autowired
    DeviceUpConsume deviceUpConsume;
    @Autowired
    ServiceInvokeConsume serviceInvokeConsume;
    @Autowired
    PropertySetConsume propertySetConsume;
    @Autowired
    DeviceReplyConsume deviceReplyConsume;
    @Autowired
    PropertyReaderConsume propertyReaderConsume;

    @Autowired
    MqttUpMessageConsume mqttUpMessageConsume;

    @Autowired
    MqttDeviceStatusConsume mqttDeviceStatusConsume;
    @Autowired
    PubMqttClient pubMqttClient;


    public void boot() {
        try {
            for (int i = 0; i < 5; i++) {
                activeMqConsume.execute();
                deviceUpConsume.execute();
                serviceInvokeConsume.execute();
                propertySetConsume.execute();
                deviceReplyConsume.execute();
                propertyReaderConsume.execute();
            }
            pubMqttClient.init();
            for (int i = 0; i < 5; i++) {
                mqttUpMessageConsume.execute();
                mqttDeviceStatusConsume.execute();
            }
        } catch (Exception e) {
            log.warn("启动监听线程异常，系统将终止", e);
            System.exit(-1);
        }

    }
}
