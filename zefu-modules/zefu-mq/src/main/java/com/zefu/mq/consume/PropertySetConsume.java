package com.zefu.mq.consume;


import com.zefu.common.base.domain.gateway.mq.PropertySetMessageBo;
import com.zefu.mq.excutor.PropertySetExecutor;
import com.zefu.mq.storage.PropertySetStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class PropertySetConsume {

    @Autowired
    PropertySetExecutor propertySetExecutor;

    /***
     * 具体线程
     */
    @Async
    public void execute() {
        while (true) {
            try {
                PropertySetMessageBo msg = PropertySetStorage.pop();
                propertySetExecutor.execute(msg);
            } catch (Exception e) {
                log.warn("内部队列消费异常", e);

            }
        }

    }

}
