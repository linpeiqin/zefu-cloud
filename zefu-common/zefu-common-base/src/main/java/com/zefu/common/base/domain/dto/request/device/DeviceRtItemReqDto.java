package com.zefu.common.base.domain.dto.request.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


@Data
public class DeviceRtItemReqDto {
    String productCode;
    String deviceCode;
    String funcType;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    Date   startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    Date   endDate;
    String identifier;
    String dataType;
}
