package com.zefu.common.base.domain.dto.response.device;

import lombok.Data;


@Data
public class GwDevicePageResDto extends DevicePageResDto {
    long deviceTotal;
    long deviceActive;
}
