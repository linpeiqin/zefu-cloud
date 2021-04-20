package com.zefu.mq.redis.publish;


import com.zefu.common.base.constants.Constants;
import com.zefu.common.base.domain.gateway.mq.PropertySetMessageBo;
import com.zefu.common.core.utils.bus.IdGenerate;
import com.zefu.common.core.utils.bus.SpringContextUtil;
import com.zefu.common.redis.service.RedisService;

public class PropertySetPublisher {
    private static RedisService cacheTemplate = SpringContextUtil.getBean(RedisService.class);

    static public void send(PropertySetMessageBo bo){
        bo.setMessageId(IdGenerate.genId());
        cacheTemplate.publish(Constants.REDIS_CHANNEL.PROP_SET, bo);
    }
}
