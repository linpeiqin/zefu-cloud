package com.zefu.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.zefu.common.security.annotation.EnableCustomConfig;
import com.zefu.common.security.annotation.EnableRyFeignClients;
import com.zefu.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 系统模块
 * 
 * @author ruoyi|linking
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class ZeFuSystemApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ZeFuSystemApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  系统模块启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
