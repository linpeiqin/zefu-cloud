package com.zefu.business.cache.impl;


import com.zefu.business.cache.IServiceInvokeLockCache;
import com.zefu.common.base.constants.Constants;
import com.zefu.common.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ServiceInvokeLockCacheImpl implements IServiceInvokeLockCache {
    @Autowired
    RedisService cacheTemplate;

    @Override
    public Boolean lock(String messageId) {
        String key = this.buildKey(messageId);
        Long ret = cacheTemplate.incr(key);
        cacheTemplate.expire(key, 3600*10);
        return ret.longValue() > 1 ? false : true;
    }

    private final String buildKey(String messageId){
        return Constants.REDIS_KEY.SERVICE_INVOKE_LOCK + messageId;
    }
}
