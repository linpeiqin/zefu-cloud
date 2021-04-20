package com.zefu.common.base.domain.dto.response.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


@Data
public class DeviceRtResDto {
    String propName;
    String identifier;
    Object value = "/";
    String unit;
    String unitName;
    String dataType;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    Object   arrivedTime;
}
