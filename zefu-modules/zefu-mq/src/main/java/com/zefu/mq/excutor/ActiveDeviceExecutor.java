package com.zefu.mq.excutor;


import com.zefu.business.api.RemoteDeviceService;
import com.zefu.common.base.constants.Constants;
import com.zefu.common.base.domain.gateway.mq.DeviceActiveMqBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ActiveDeviceExecutor {
    @Autowired
    RemoteDeviceService deviceService;

    @Async(Constants.TASK.DEVICE_ACTIVE_NAME)
    public void execute(DeviceActiveMqBo bo){
        try {
            deviceService.activeDevice(bo);
        } catch (Exception e) {
            log.warn("", e);
        }
    }

}
