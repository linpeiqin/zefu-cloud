package com.zefu.mq.storage;


import com.zefu.common.base.domain.gateway.mq.PropertyReaderMessageBo;
import com.zefu.common.base.enums.ErrorEnum;
import com.zefu.common.core.exception.BaseException;

import java.util.concurrent.LinkedBlockingQueue;



public class PropertyReaderStorage {
    static private LinkedBlockingQueue<PropertyReaderMessageBo> queue = new LinkedBlockingQueue<>();

    static public void push(PropertyReaderMessageBo e) {
         queue.offer(e);
    }
    static public int size(){
        return queue.size();
    }
    static public PropertyReaderMessageBo pop() {
        try{
            return queue.take();
        }catch (Exception e){
            throw new BaseException(ErrorEnum.INNER_EXCEPTION.getMsg(), "属性设备读取队列消费异常");
        }
    }
}
