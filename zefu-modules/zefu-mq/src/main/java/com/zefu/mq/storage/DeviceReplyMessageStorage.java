package com.zefu.mq.storage;


import com.zefu.common.base.domain.gateway.mq.DeviceUpMessageBo;
import com.zefu.common.base.enums.ErrorEnum;
import com.zefu.common.core.exception.BaseException;

import java.util.concurrent.LinkedBlockingQueue;



public class DeviceReplyMessageStorage {
    static private LinkedBlockingQueue<DeviceUpMessageBo> queue = new LinkedBlockingQueue<>();

    static public void push(DeviceUpMessageBo e) {
         queue.offer(e);
    }
    static public int size(){
        return queue.size();
    }
    static public DeviceUpMessageBo pop() {
        try{
            return queue.take();
        }catch (Exception e){
            throw new BaseException(ErrorEnum.INNER_EXCEPTION.getMsg(), "数据上报消费异常");
        }

    }
}
