package com.zefu.common.base.biz;


import com.zefu.common.base.constants.Constants;
import com.zefu.common.base.metadata.ProductFuncTypeEnum;

public class RedisKeyUtil {
    /**
     * 构造设备实时状态缓存key
     *
     * */
    public static String buildRtCacheKey( String deviceCode, ProductFuncTypeEnum funcType){
        return Constants.REDIS_KEY.DEV_RT_DATA + deviceCode + ":" + funcType.getType();
    }
    public static String buildDeviceInfoKey( String deviceCode){
        return Constants.REDIS_KEY.DEV_INFO + deviceCode;
    }

    public static String buildDeviceOfflineKey( String deviceCode){
        return Constants.REDIS_KEY.DEVICE_OFFLINE + deviceCode;
    }

    public static String buildProductInfoKey( String productCode){
        return Constants.REDIS_KEY.PRODUCT_INFO + productCode;
    }

    public static String buildAuthKey( String key){
        return Constants.REDIS_KEY.OPEN_AUTH_INFO + key;
    }
}
