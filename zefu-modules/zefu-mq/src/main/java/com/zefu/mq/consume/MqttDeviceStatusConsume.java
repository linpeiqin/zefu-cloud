package com.zefu.mq.consume;

import com.zefu.mq.excutor.MqttDeviceStatusExecutor;
import com.zefu.mq.excutor.MqttUpMessageExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MqttDeviceStatusConsume {
    @Autowired
    MqttDeviceStatusExecutor mqttDeviceStatusExecutor;
    @Async
    public void execute() throws InterruptedException {
            try{
                mqttDeviceStatusExecutor.execute();
            }catch (Exception e){
                log.warn("",e);
            }
    }
}
