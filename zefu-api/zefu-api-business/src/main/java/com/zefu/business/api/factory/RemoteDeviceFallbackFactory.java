package com.zefu.business.api.factory;

import com.zefu.business.api.RemoteDeviceService;
import com.zefu.common.base.domain.dto.response.device.DevicePageResDto;
import com.zefu.common.base.domain.gateway.mq.DeviceActiveMqBo;
import com.zefu.common.base.enums.BatchOpEnum;
import com.zefu.common.core.domain.R;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 设备服务降级处理
 * 
 * @author ruoyi|linking
 */
@Component
public class RemoteDeviceFallbackFactory implements FallbackFactory<RemoteDeviceService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteDeviceFallbackFactory.class);

    @Override
    public RemoteDeviceService create(Throwable throwable)
    {
        log.error("设备服务调用失败:{}", throwable.getMessage());
        return new RemoteDeviceService()
        {
            @Override
            public R<Boolean> activeDevice(DeviceActiveMqBo bo) {
                return null;
            }

            @Override
            public R<Boolean> batchChangeStatusByCode(String[] devices, BatchOpEnum batchOpEnum) {
                return null;
            }

            @Override
            public R<DevicePageResDto> queryByDevCode(String devCode) {
                return R.fail("服务调用失败。。。。");
            }
        };
    }
}
