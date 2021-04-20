package com.zefu.mq.storage;



import com.zefu.common.base.domain.gateway.mq.PropertySetMessageBo;
import com.zefu.common.base.enums.ErrorEnum;
import com.zefu.common.core.exception.BaseException;

import java.util.concurrent.LinkedBlockingQueue;



public class PropertySetStorage {
    static private LinkedBlockingQueue<PropertySetMessageBo> queue = new LinkedBlockingQueue<>();

    static public void push(PropertySetMessageBo e) {
         queue.offer(e);
    }
    static public int size(){
        return queue.size();
    }
    static public PropertySetMessageBo pop() {
        try{
            return queue.take();
        }catch (Exception e){
            throw new BaseException(ErrorEnum.INNER_EXCEPTION.getMsg(), "属性设备队列消费异常");
        }
    }
}
