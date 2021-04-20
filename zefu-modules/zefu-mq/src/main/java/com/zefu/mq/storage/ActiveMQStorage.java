package com.zefu.mq.storage;


import com.zefu.common.base.domain.gateway.mq.DeviceActiveMqBo;
import com.zefu.common.base.enums.ErrorEnum;
import com.zefu.common.core.exception.BaseException;

import java.util.concurrent.LinkedBlockingQueue;


public class ActiveMQStorage {
    static private LinkedBlockingQueue<DeviceActiveMqBo> queue = new LinkedBlockingQueue<>();

    static public void push(DeviceActiveMqBo e) {
         queue.offer(e);
    }

    static public int size(){
        return queue.size();
    }
    static public DeviceActiveMqBo pop() {
        try{
            return queue.take();
        }catch (Exception e){
            throw new BaseException(ErrorEnum.INNER_EXCEPTION.getMsg(), "设备在线状态变更消费异常");
        }
    }
}
