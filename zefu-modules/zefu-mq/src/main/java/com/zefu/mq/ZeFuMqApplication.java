package com.zefu.mq;

import com.zefu.common.security.annotation.EnableCustomConfig;
import com.zefu.common.security.annotation.EnableRyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.lang.annotation.*;

/**
 * 消息服务
 * 
 * @author ruoyi|linking
 */
@EnableRyFeignClients
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableElasticsearchRepositories(basePackages = "com.zefu.common.storage.es")
public class ZeFuMqApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ZeFuMqApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  消息服务模块启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
