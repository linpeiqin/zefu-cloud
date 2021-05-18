package com.zefu.mq.redis.publish;


import com.zefu.common.base.constants.Constants;
import com.zefu.common.base.domain.gateway.mq.DeviceActiveMqBo;
import com.zefu.common.core.utils.bus.SpringContextUtil;
import com.zefu.common.redis.service.RedisService;

public class OnlinePublisher {
    private static RedisService cacheTemplate = SpringContextUtil.getBean(RedisService.class);

    static public void send(DeviceActiveMqBo bo){
        cacheTemplate.publish(Constants.REDIS_CHANNEL.ONLINE, bo);
    }
}
