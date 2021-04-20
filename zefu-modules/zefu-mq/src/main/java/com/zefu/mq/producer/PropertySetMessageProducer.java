package com.zefu.mq.producer;


import com.zefu.common.base.domain.gateway.mq.PropertySetMessageBo;
import com.zefu.mq.storage.PropertySetStorage;

public class PropertySetMessageProducer {
    static public void send(PropertySetMessageBo bo){
        PropertySetStorage.push(bo);
    }
}
