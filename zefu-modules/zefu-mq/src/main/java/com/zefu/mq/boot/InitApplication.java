package com.zefu.mq.boot;


import com.zefu.common.base.biz.EsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class InitApplication implements ApplicationRunner {

    @Autowired
    GatewayBootService gatewayBootService;
    @Value("${spring.profiles.active}")
    private String env;

    @Override
    public void run(ApplicationArguments args) {
        try {
            gatewayBootService.boot();
            EsUtil.setEnv(env);
        } catch (Exception e) {
            log.error("启动异常", e);
            System.exit(-1);
        }
    }
}
