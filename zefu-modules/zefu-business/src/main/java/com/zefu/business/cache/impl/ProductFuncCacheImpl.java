package com.zefu.business.cache.impl;


import com.zefu.business.cache.IProductFuncCache;
import com.zefu.business.domain.BusProductFunc;

import com.zefu.common.base.constants.Constants;
import com.zefu.common.core.utils.bus.JSONProvider;
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
            redisService.addHashMapTime(key, item.getIdentifier(), item, Constants.REDIS_DEF.GENERAL_EXPIRED);
        }
    }

    @Override
    public void setProperty(String productCode, String funcType, BusProductFunc po) {
        if (null == po) {
            return;
        }
        String key = this.buildModelKey(productCode, funcType);
        redisService.addHashMapTime(key, po.getIdentifier(),po,
                Constants.REDIS_DEF.GENERAL_EXPIRED);
    }

    @Override
    public BusProductFunc getProperty(String productCode, String funcType, String identifier, Integer funcStatus) {
        String key = this.buildModelKey(productCode, funcType);
        BusProductFunc po = redisService.getCacheMapValue(key, identifier);
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
        Map<String, BusProductFunc> map = redisService.getCacheMap(key);
        if (CollectionUtils.isEmpty(map)) {
            return resultMap;
        }
        map.forEach((filed, value) -> {
            if (null != value) {
                if (null != funcStatus) {
                    if (funcStatus.equals(value.getFuncStatus())) {
                        resultMap.put(filed, value);
                    }
                } else {
                    resultMap.put(filed, value);
                }
            }
        });
        return resultMap;
    }

    @Override
    public List<BusProductFunc> getListProperties(String productCode, String funcType, Integer funcStatus) {
        List<BusProductFunc> resultList = new ArrayList<>();
        String key = this.buildModelKey(productCode, funcType);
        Map<String, BusProductFunc> map = redisService.getCacheMap(key);
        if (CollectionUtils.isEmpty(map)) {
            return resultList;
        }
        map.forEach((filed, value) -> {
            if (null != value) {
                if (null != funcStatus) {
                    if (funcStatus.equals(value.getFuncStatus())) {
                        resultList.add(value);
                    }
                } else {
                    resultList.add(value);
                }
            }
        });
        return resultList;
    }

    private final String buildModelKey(String productCode, String funcType) {
        return Constants.REDIS_KEY.PRODUCT_FUNC + productCode + ":" + funcType;
    }

}
