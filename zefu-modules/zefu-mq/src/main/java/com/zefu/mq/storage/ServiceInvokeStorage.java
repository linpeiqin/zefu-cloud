package com.zefu.mq.storage;



import com.zefu.common.base.domain.gateway.mq.ServiceInvokeMessageBo;
import com.zefu.common.base.enums.ErrorEnum;
import com.zefu.common.core.exception.BaseException;

import java.util.concurrent.LinkedBlockingQueue;


public class ServiceInvokeStorage {
    static private LinkedBlockingQueue<ServiceInvokeMessageBo> queue = new LinkedBlockingQueue<>();

    static public void push(ServiceInvokeMessageBo e) {
         queue.offer(e);
    }
    static public int size(){
        return queue.size();
    }
    static public ServiceInvokeMessageBo pop() {
        try{
            return queue.take();
        }catch (Exception e){
            throw new BaseException(ErrorEnum.INNER_EXCEPTION.getMsg(), "服务调用队列消费异常");
        }
    }
}
