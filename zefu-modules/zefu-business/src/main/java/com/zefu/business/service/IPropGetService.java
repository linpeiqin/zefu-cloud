package com.zefu.business.service;


import com.zefu.common.base.domain.gateway.direct.response.PropertyGetResponse;

public interface IPropGetService {
    /**
     * 服务端实时服务设备属性
     * @param deviceCode
     * @param identifier
     * @param timeout 超时时间，单位秒
     * @return 如果value是null的话 说明是超时为收到设备上报数据
     * */
    PropertyGetResponse fetchProperty(String deviceCode, String identifier, Long timeout);
}
