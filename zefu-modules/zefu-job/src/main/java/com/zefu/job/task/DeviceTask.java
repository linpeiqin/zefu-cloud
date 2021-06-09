package com.zefu.job.task;

import org.springframework.stereotype.Component;

/**
 * 设备状态同步任务
 *
 * @author ruoyi|linking
 */
@Component("deviceTask")
public class DeviceTask {

    public void clientOnlineStatus()
    {

        System.out.println("30s任务执行了。。。。");
    }
}
