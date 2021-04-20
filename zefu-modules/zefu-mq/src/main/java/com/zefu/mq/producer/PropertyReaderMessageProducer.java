package com.zefu.mq.producer;


import com.zefu.common.base.domain.gateway.mq.PropertyReaderMessageBo;
import com.zefu.mq.storage.PropertyReaderStorage;

public class PropertyReaderMessageProducer {
    static public void send(PropertyReaderMessageBo bo){
        PropertyReaderStorage.push(bo);
    }
}
