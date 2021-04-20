package com.zefu.business.cache;


import com.zefu.business.domain.BusDeviceManage;
import com.zefu.common.base.domain.dto.response.device.DevicePageResDto;

import java.util.Map;

public interface IDeviceCache {
    /**根据设备编码读取设备缓存信息
     * @param deviceCode
     * @return
     * */
    DevicePageResDto deviceReader(String deviceCode);
    /**
     * 根据设备编码写入设备缓存信息
     * @param deviceCode
     * @param data
     * */
    void deviceWriter(String deviceCode, DevicePageResDto data);
    /**
     * 移除缓存
     * @param deviceCode
     * */
    void remove(String deviceCode);

    Map<String, String> getHashMapAll(String redisKey);

    void removeHashMap(String redisKey, String field);


    void publish(String key, Object bo);
}
