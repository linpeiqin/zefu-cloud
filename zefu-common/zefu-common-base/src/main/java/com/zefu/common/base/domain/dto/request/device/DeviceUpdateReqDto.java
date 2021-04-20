package com.zefu.common.base.domain.dto.request.device;

import lombok.Data;


@Data
public class DeviceUpdateReqDto {
    Long  id;
    String deviceName ;
    String gwDevCode ;
    String productCode ;
    String deviceCode  ;
    String deviceSecret  ;
}
