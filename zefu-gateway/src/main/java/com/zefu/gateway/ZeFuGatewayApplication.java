package com.zefu.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 网关启动程序
 * 
 * @author ruoyi|linking
 */

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ZeFuGatewayApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ZeFuGatewayApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  泽福网关启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
