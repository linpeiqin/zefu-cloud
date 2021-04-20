package com.zefu.business.cache.impl;


import com.zefu.business.cache.IPropertyGetCache;
import com.zefu.common.base.constants.Constants;
import com.zefu.common.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class PropertyGetCacheImpl implements IPropertyGetCache {
    @Autowired
    RedisService cacheTemplate;

    @Override
    public void propGetValueWrite(String messageId, Object value) {
        if (null == value) {
            return;
        }
        cacheTemplate.set(buildPropGetKey(messageId), value, Constants.REDIS_DEF.GENERAL_EXPIRED);
    }

    @Override
    public <T> T propGetReader(String messageId, Class<T> tClass) {
        return cacheTemplate.get(this.buildPropGetKey(messageId), tClass);
    }

    @Override
    public Map<String, Object> propGetReader(String messageId) {
        return cacheTemplate.get(this.buildPropGetKey(messageId), HashMap.class);
    }

    private final String buildPropGetKey(String messageId) {
        return Constants.REDIS_KEY.PROPERTY_GET + messageId;
    }
}
