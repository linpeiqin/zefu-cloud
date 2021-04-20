package com.zefu.mq.producer;


import com.zefu.common.base.domain.gateway.mq.DeviceActiveMqBo;
import com.zefu.mq.storage.ActiveMQStorage;


public class DeviceActiveMqProducer {
    static public void send(DeviceActiveMqBo bo){
        ActiveMQStorage.push(bo);
    }
}
