package com.zefu.business.api;

import com.zefu.business.api.factory.RemoteDeviceFallbackFactory;
import com.zefu.common.base.domain.dto.response.device.DevicePageResDto;
import com.zefu.common.base.domain.gateway.mq.DeviceActiveMqBo;
import com.zefu.common.base.enums.BatchOpEnum;
import com.zefu.common.core.constant.ServiceNameConstants;
import com.zefu.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 设备服务
 * 
 * @author ruoyi|linking
 */
@FeignClient(contextId = "remoteDeviceService", value = ServiceNameConstants.BUSINESS_SERVICE, fallbackFactory = RemoteDeviceFallbackFactory.class)
public interface RemoteDeviceService
{

    @PostMapping(value = "/device/activeDevice")
    public R<Boolean> activeDevice(@RequestParam("bo") DeviceActiveMqBo bo);
    @PostMapping(value = "/device/batchChangeStatusByCode")
    public R<Boolean> batchChangeStatusByCode(@RequestParam("devices") List<String> devices, @RequestParam("batchOpEnum") BatchOpEnum batchOpEnum);
    @GetMapping (value = "/device/queryByDevCode")
    public R<DevicePageResDto> queryByDevCode(@RequestParam("devCode") String devCode);

}
