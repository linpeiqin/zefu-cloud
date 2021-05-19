package com.zefu.business.cache.impl;


import com.zefu.business.cache.IDeviceOfflineCache;
import com.zefu.common.base.biz.RedisKeyUtil;
import com.zefu.common.base.constants.Constants;
import com.zefu.common.base.domain.gateway.mq.DeviceActiveMqBo;
import com.zefu.common.base.enums.BatchOpEnum;
import com.zefu.common.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;


@Service
public class DeviceOfflineCacheImpl implements IDeviceOfflineCache {
    @Autowired
    private RedisService cacheTemplate;
    @Value("${zefu.device.ping.expire:120000}")
    private Integer expired;

    @Override
    public void cacheWriter(DeviceActiveMqBo deviceActiveMqBo) {
        String key = this.buildKey(deviceActiveMqBo.getDeviceCode());
        if(!deviceActiveMqBo.getActive()){
            cacheTemplate.deleteObject(key);
            cacheTemplate.zRem(Constants.REDIS_KEY.DEVICE_ONLINE, deviceActiveMqBo.getDeviceCode());
        }else {
            cacheTemplate.set(key, deviceActiveMqBo, expired/1000);
            Double score = new Double(System.currentTimeMillis()/1000);
            cacheTemplate.zAdd(Constants.REDIS_KEY.DEVICE_ONLINE, score, deviceActiveMqBo.getDeviceCode());
        }

    }

    @Override
    public DeviceActiveMqBo cacheReader(String deviceCode) {
        String key = this.buildKey(deviceCode);
        return cacheTemplate.get(key, DeviceActiveMqBo.class);
    }

    @Override
    public Long activeCount() {
        return cacheTemplate.zcard(Constants.REDIS_KEY.DEVICE_ONLINE);
    }

    @Override
    public Map<String, DeviceActiveMqBo> cacheMapReader(List<String> deviceCodes) {
        Map<String, DeviceActiveMqBo> map = new HashMap<>();
        List<DeviceActiveMqBo> list = this.cacheBatchReader(deviceCodes);
        for(DeviceActiveMqBo item:list){
            map.put(item.getDeviceCode(), item);
        }
        return map;
    }

    @Override
    public List<DeviceActiveMqBo> cacheBatchReader(List<String> deviceCodes) {
        if(CollectionUtils.isEmpty(deviceCodes)){
            return null;
        }
        List<String> keys = new ArrayList<>();
        for(String item: deviceCodes){
            String key = this.buildKey(item);
            keys.add(key);
        }
        List<DeviceActiveMqBo> list = cacheTemplate.mget(keys, DeviceActiveMqBo.class);

        return list;
    }

    @Override
    public void cacheBatchWriter(List<String> deviceCodes, BatchOpEnum batchOpEnum) {
        if(CollectionUtils.isEmpty(deviceCodes)){
            return ;
        }
        for(String deviceCode : deviceCodes){
            DeviceActiveMqBo bo = new DeviceActiveMqBo();
            bo.setDeviceCode(deviceCode);
            Boolean activeStatus = batchOpEnum.equals(BatchOpEnum.ONLINE) ? true : false;
            bo.setActive(activeStatus);
            bo.setTimestamp(new Date());
            this.cacheWriter(bo);
        }

    }

    @Override
    public void removeExpire() {
        Long now = System.currentTimeMillis()/1000;
        Double max = new Double(now - expired/1000);
        cacheTemplate.zRemRangeByScore(Constants.REDIS_KEY.DEVICE_ONLINE, 0, max);
    }

    private final String buildKey(String deviceCode){
        return  RedisKeyUtil.buildDeviceOfflineKey(deviceCode);
    }
}
