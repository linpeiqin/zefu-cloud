package com.zefu.gen;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import com.zefu.common.security.annotation.EnableCustomConfig;
import com.zefu.common.security.annotation.EnableRyFeignClients;
import com.zefu.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 代码生成
 * 
 * @author ruoyi|linking
 */
@EnableCustomConfig
@EnableCustomSwagger2   
@EnableRyFeignClients
@SpringCloudApplication
public class ZeFuGenApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ZeFuGenApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  代码生成模块启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
