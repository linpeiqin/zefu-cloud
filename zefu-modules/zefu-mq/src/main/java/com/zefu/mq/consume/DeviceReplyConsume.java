package com.zefu.mq.consume;


import com.zefu.common.base.domain.gateway.mq.DeviceUpMessageBo;
import com.zefu.mq.excutor.DeviceMessageReplyExecutor;
import com.zefu.mq.storage.DeviceReplyMessageStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeviceReplyConsume {
    @Autowired
    private DeviceMessageReplyExecutor deviceMessageReplyExecutor;

    @Async
    public void execute() {
        while (true) {
            try {
                DeviceUpMessageBo msg = DeviceReplyMessageStorage.pop();
                deviceMessageReplyExecutor.execute(msg);
            } catch (Exception e) {
                log.warn("内部队列消费异常", e);
                continue;
            }
        }
    }

}
