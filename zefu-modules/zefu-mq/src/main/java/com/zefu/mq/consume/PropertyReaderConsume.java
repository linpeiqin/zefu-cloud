package com.zefu.mq.consume;


import com.zefu.common.base.domain.gateway.mq.PropertyReaderMessageBo;
import com.zefu.mq.excutor.PropertyReaderExecutor;
import com.zefu.mq.storage.PropertyReaderStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class PropertyReaderConsume {

    @Autowired
    PropertyReaderExecutor propertyReaderExecutor;

    /***
     * 具体线程
     */
    @Async
    public void execute() {
        while (true) {
            try {
                PropertyReaderMessageBo msg = PropertyReaderStorage.pop();
                propertyReaderExecutor.execute(msg);
            } catch (Exception e) {
                log.warn("内部队列消费异常", e);

            }
        }

    }

}
