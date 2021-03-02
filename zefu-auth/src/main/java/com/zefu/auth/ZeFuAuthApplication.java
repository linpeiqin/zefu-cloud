package com.zefu.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import com.zefu.common.security.annotation.EnableRyFeignClients;

/**
 * 认证授权中心
 * 
 * @author ruoyi|linking
 */
@EnableRyFeignClients
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ZeFuAuthApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ZeFuAuthApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  认证授权中心启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
