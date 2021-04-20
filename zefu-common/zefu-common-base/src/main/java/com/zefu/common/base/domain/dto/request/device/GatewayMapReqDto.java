package com.zefu.common.base.domain.dto.request.device;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
public class GatewayMapReqDto {
    @NotNull(message = "网关设备编码不能为空")
    String       gwDeviceCode;
    @NotEmpty(message = "子设备列表不能为空")
    List<String> devices;
    Boolean removeAction = false;
}
