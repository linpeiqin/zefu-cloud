package com.zefu.mq.consume;


import com.zefu.common.base.domain.gateway.mq.DeviceUpMessageBo;
import com.zefu.mq.excutor.DeviceMessageUpExecutor;
import com.zefu.mq.storage.DeviceUpMessageStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class DeviceUpConsume {
    @Autowired
    private DeviceMessageUpExecutor deviceMessageUpExecutor;

    @Async
    public void execute() {
        while (true) {
            try {
                DeviceUpMessageBo msg = DeviceUpMessageStorage.pop();
                deviceMessageUpExecutor.execute(msg);
            } catch (Exception e) {
                log.warn("内部队列消费异常", e);
                continue;
            }
        }
    }

}
