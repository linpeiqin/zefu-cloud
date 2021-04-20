package com.zefu.mq.redis.publish;


import com.zefu.common.base.constants.Constants;
import com.zefu.common.base.domain.gateway.mq.ServiceInvokeMessageBo;
import com.zefu.common.core.utils.bus.SpringContextUtil;
import com.zefu.common.redis.service.RedisService;

public class ServiceInvokePublisher {
    private static RedisService cacheTemplate = SpringContextUtil.getBean(RedisService.class);

    static public void send(ServiceInvokeMessageBo bo){
        cacheTemplate.publish(Constants.REDIS_CHANNEL.SERVICE_INVOKE, bo);
    }
}
