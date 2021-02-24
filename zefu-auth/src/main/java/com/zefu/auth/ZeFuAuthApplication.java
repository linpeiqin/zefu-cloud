package com.zefu.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import com.zefu.common.security.annotation.EnableRyFeignClients;

/**
 * 认证授权中心
 * 
 * @author ruoyi|linking
 */
@EnableRyFeignClients
@SpringCloudApplication
public class ZeFuAuthApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ZeFuAuthApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  认证授权中心启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
