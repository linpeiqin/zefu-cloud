package com.zefu.mq.consume;


import com.zefu.common.base.domain.gateway.mq.ServiceInvokeMessageBo;
import com.zefu.mq.excutor.ServiceInvokeExecutor;
import com.zefu.mq.storage.ServiceInvokeStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class ServiceInvokeConsume {

    @Autowired
    ServiceInvokeExecutor serviceInvokeExecutor;

    /***
     * 具体线程
     */
    @Async
    public void execute() {
        while (true) {
            try {
                ServiceInvokeMessageBo msg = ServiceInvokeStorage.pop();
                serviceInvokeExecutor.execute(msg);
            } catch (Exception e) {
                log.warn("内部队列消费异常", e);

            }
        }
    }

}
