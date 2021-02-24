package com.zefu.job;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import com.zefu.common.security.annotation.EnableCustomConfig;
import com.zefu.common.security.annotation.EnableRyFeignClients;
import com.zefu.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 定时任务
 * 
 * @author ruoyi|linking
 */
@EnableCustomConfig
@EnableCustomSwagger2   
@EnableRyFeignClients
@SpringCloudApplication
public class ZeFuJobApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ZeFuJobApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  定时任务模块启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
