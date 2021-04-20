package com.zefu.business.cache.impl;


import com.zefu.business.cache.IMessageCountCache;
import com.zefu.common.base.constants.Constants;
import com.zefu.common.base.domain.dto.response.dashboard.DashLineResDto;
import com.zefu.common.core.utils.bus.DateUtil;
import com.zefu.common.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;



@Service
@Slf4j
public class MessageCountCacheImpl implements IMessageCountCache {
    @Autowired
    RedisService cacheTemplate;

    @Override
    public Integer todayTotal() {
        String key = this.buildTodayKey();
        String value = cacheTemplate.get(key, String.class);
        return StringUtils.isEmpty(value) ? null : Integer.valueOf(value);
    }

    @Override
    public void  todayTotalIncr() {
        String key = this.buildTodayKey();
        try{
            Long ret = cacheTemplate.incr(key);
            if(ret < 5){
                cacheTemplate.expire(key, 60*60*25);
            }
        }catch (Exception e){
            log.warn("消息[{}]自增长异常", key, e);
        }

    }

    @Override
    public DashLineResDto dashLineRead() {
        String key = buildDashLineKey();
        return cacheTemplate.get(key, DashLineResDto.class);
    }

    @Override
    public void dashLineWrite(DashLineResDto resDto) {
        String key = buildDashLineKey();
        cacheTemplate.set(key, resDto);
    }

    private final String buildDashLineKey(){
        String today = DateUtil.getTodayString();
        return Constants.REDIS_KEY.MESSAGE_DASH_LINE + today;
    }
    private String buildTodayKey() {
        String today = DateUtil.getTodayString();
        return Constants.REDIS_KEY.MESSAGE_TODAY + today;
    }
}
