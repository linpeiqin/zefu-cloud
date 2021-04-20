package com.zefu.mq.consume;

import com.zefu.common.base.domain.gateway.mq.DeviceActiveMqBo;
import com.zefu.mq.excutor.ActiveDeviceExecutor;
import com.zefu.mq.storage.ActiveMQStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ActiveMqConsume {
    @Autowired
    ActiveDeviceExecutor activeDeviceExecutor;
    @Async
    public void execute() throws InterruptedException {
         while(true){
             try{
                 DeviceActiveMqBo bo = ActiveMQStorage.pop();
                 activeDeviceExecutor.execute(bo);
             }catch (Exception e){
                 log.warn("",e);
                 continue;
             }
         }
    }

}
