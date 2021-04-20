package com.zefu.common.base.domain.dto.request.device;

import lombok.Data;


@Data
public class DeviceImportReqDto {
    String deviceCode;
    String productCode;
    String deviceName;
    String deviceSecret;
}
