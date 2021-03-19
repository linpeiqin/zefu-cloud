package com.zefu.business.cache.impl;


import com.zefu.business.cache.IProductFuncCache;
import com.zefu.business.domain.BusProductFunc;
import com.zefu.common.core.constant.ComConstants;
import com.zefu.common.core.utils.JSONProvider;
import com.zefu.common.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProductFuncCacheImpl implements IProductFuncCache {
    @Autowired
    RedisService redisService;

    @Override
    public void remove(String productCode, String funcType) {
        String key = this.buildModelKey(productCode, funcType);
        redisService.deleteObject(key);
    }

    @Override
    public void removeField(String productCode, String funcType, String identifier) {
        String key = this.buildModelKey(productCode, funcType);
        redisService.removeHashMap(key, identifier);
    }

    @Override
    public void removeKey(String productCode, String funcType) {
        String key = this.buildModelKey(productCode, funcType);
        redisService.deleteObject(key);
    }

    @Override
    public void setProperties(String productCode, String funcType, List<BusProductFunc> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        String key = this.buildModelKey(productCode, funcType);
        for (BusProductFunc item : list) {
            if (null == item) {
                continue;
            }
            String value = JSONProvider.toJSONString(item);
            redisService.addHashMapTime(key, item.getIdentifier(), value, ComConstants.REDIS_DEF.GENERAL_EXPIRED);
        }
    }

    @Override
    public void setProperty(String productCode, String funcType, BusProductFunc po) {
        if (null == po) {
            return;
        }
        String key = this.buildModelKey(productCode, funcType);
        redisService.addHashMapTime(key, po.getIdentifier(), JSONProvider.toJSONString(po),
                ComConstants.REDIS_DEF.GENERAL_EXPIRED);
    }

    @Override
    public BusProductFunc getProperty(String productCode, String funcType, String identifier, Integer funcStatus) {
        String key = this.buildModelKey(productCode, funcType);
        String value = redisService.getHashMap(key, identifier);
        BusProductFunc po = JSONProvider.parseObject(value, BusProductFunc.class);
        if (null == po) {
            return null;
        }
        if (null != funcStatus) {
            return funcStatus.equals(po.getFuncStatus()) ? po : null;
        }
        return po;
    }

    @Override
    public Map<String, BusProductFunc> getProperties(String productCode, String funcType, Integer funcStatus) {
        Map<String, BusProductFunc> resultMap = new HashMap<>();
        String key = this.buildModelKey(productCode, funcType);
        Map<String, String> map = redisService.getCacheMap(key);
        if (CollectionUtils.isEmpty(map)) {
            return resultMap;
        }
        map.forEach((filed, value) -> {
            BusProductFunc po = JSONProvider.parseObject(value, BusProductFunc.class);
            if (null != po) {
                if (null != funcStatus) {
                    if (funcStatus.equals(po.getFuncStatus())) {
                        resultMap.put(filed, po);
                    }
                } else {
                    resultMap.put(filed, po);
                }
            }
        });
        return resultMap;
    }

    @Override
    public List<BusProductFunc> getListProperties(String productCode, String funcType, Long funcStatus) {
        List<BusProductFunc> resultList = new ArrayList<>();
        String key = this.buildModelKey(productCode, funcType);
        Map<String, String> map = redisService.getCacheMap(key);
        if (CollectionUtils.isEmpty(map)) {
            return resultList;
        }
        map.forEach((filed, value) -> {
            BusProductFunc po = JSONProvider.parseObject(value, BusProductFunc.class);
            if (null != po) {
                if (null != funcStatus) {
                    if (funcStatus.equals(po.getFuncStatus())) {
                        resultList.add(po);
                    }
                } else {
                    resultList.add(po);
                }
            }
        });
        return resultList;
    }

    private final String buildModelKey(String productCode, String funcType) {
        return ComConstants.REDIS_KEY.PRODUCT_FUNC + productCode + ":" + funcType;
    }

}
