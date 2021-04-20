package com.zefu.mq.producer;


import com.zefu.common.base.domain.gateway.mq.ServiceInvokeMessageBo;
import com.zefu.mq.storage.ServiceInvokeStorage;

public class ServiceInvokeMessageProducer {
    static public void send(ServiceInvokeMessageBo bo){
        ServiceInvokeStorage.push(bo);
    }
}
