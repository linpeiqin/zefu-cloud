package com.zefu.business.cache.impl;


import com.zefu.business.cache.IJobLockCache;
import com.zefu.common.base.constants.Constants;
import com.zefu.common.core.utils.bus.DateUtil;
import com.zefu.common.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;


@Slf4j
@Service
public class JobLockCacheImpl implements IJobLockCache {
    @Autowired
    RedisService cacheTemplate;

    @Override
    public Boolean lock(String jobName, Integer seconds) {
        String key = this.buildKey(jobName);
        Date date = new Date();
        String data = DateUtil.getStringByFormat(date, DateUtil.newFormat);
        return cacheTemplate.setNx(key, data, seconds);
    }

    @Override
    public void unlock(String jobName) {
        String key = this.buildKey(jobName);
        cacheTemplate.deleteObject(key);
    }

    @Override
    public Date getLock(String jobName) {
        try{
            String key = this.buildKey(jobName);
            String data = cacheTemplate.getCacheObject(key);
            if(StringUtils.isEmpty(data)){
                return null;
            }
            return DateUtil.parseDateNewFormat(data);
        }catch (Exception e){
            log.warn("获取时间异常", e);
            return null;
        }

    }

    private final String buildKey(String jobName){
        return Constants.REDIS_KEY.JOB_LOCK + jobName;
    }
}
