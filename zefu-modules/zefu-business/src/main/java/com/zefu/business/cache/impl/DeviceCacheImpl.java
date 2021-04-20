package com.zefu.business.cache.impl;


import com.zefu.business.cache.IDeviceCache;
import com.zefu.business.cache.IDeviceOfflineCache;
import com.zefu.common.base.constants.Constants;
import com.zefu.common.base.domain.dto.response.device.DevicePageResDto;
import com.zefu.common.base.domain.gateway.mq.DeviceActiveMqBo;
import com.zefu.common.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.zefu.common.base.constants.Constants.REDIS_KEY.DEV_INFO;


@Service
public class DeviceCacheImpl implements IDeviceCache {
    @Autowired
    RedisService cacheTemplate;
    @Autowired
    IDeviceOfflineCache deviceOfflineCache;
    @Override
    public DevicePageResDto deviceReader(String deviceCode) {
        String key = this.buildDevKey(deviceCode);

        DevicePageResDto busDeviceManage = cacheTemplate.get(key, DevicePageResDto.class);
        if(null == busDeviceManage){
            return null;
        }
        DeviceActiveMqBo activeMqBo = deviceOfflineCache.cacheReader(deviceCode);
        if(null != activeMqBo){
            busDeviceManage.setDevPort(activeMqBo.getPort());
            busDeviceManage.setDevHost(activeMqBo.getHost());
            busDeviceManage.setActiveStatus(activeMqBo.getActive() ? 1 : 0);
            busDeviceManage.setLastOnlineTime(activeMqBo.getTimestamp());
        }
        return busDeviceManage;
    }

    @Override
    public void deviceWriter(String deviceCode, DevicePageResDto data) {
        String key = this.buildDevKey(deviceCode);
        cacheTemplate.set(key, data, Constants.REDIS_DEF.DEVICE_INFO_EXPIRED);
    }

    @Override
    public void remove(String deviceCode) {
        String key = this.buildDevKey(deviceCode);
        cacheTemplate.deleteObject(key);
    }

    @Override
    public Map<String, String> getHashMapAll(String redisKey) {
        return cacheTemplate.getHashMapAll(redisKey);
    }

    @Override
    public void removeHashMap(String redisKey, String field) {
        cacheTemplate.removeHashMap(redisKey,field);
    }


    @Override
    public void publish(String key, Object bo) {
        cacheTemplate.publish(key, bo);
    }

    private final String buildDevKey(String deviceCode){
        return  DEV_INFO + deviceCode;
    }
}
