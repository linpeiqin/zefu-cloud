package com.zefu.mq.producer;


import com.zefu.common.base.domain.gateway.mq.DeviceUpMessageBo;
import com.zefu.mq.storage.DeviceUpMessageStorage;

public class DeviceUpMessageProducer {
    static public void send(DeviceUpMessageBo bo){
        DeviceUpMessageStorage.push(bo);
    }
}
