package com.zefu.common.base.domain.dto.request.device;

import lombok.Data;


@Data public class DevCreateReqDto {
    String deviceCode = "";
    String deviceName = "";
    String gwDevCode = "";
    String productCode = "";
    String deviceSecret = "";
}
