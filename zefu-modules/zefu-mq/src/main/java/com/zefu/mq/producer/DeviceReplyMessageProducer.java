package com.zefu.mq.producer;


import com.zefu.common.base.domain.gateway.mq.DeviceUpMessageBo;
import com.zefu.mq.storage.DeviceReplyMessageStorage;

public class DeviceReplyMessageProducer {
    static public void send(DeviceUpMessageBo bo){
        DeviceReplyMessageStorage.push(bo);
    }
}
