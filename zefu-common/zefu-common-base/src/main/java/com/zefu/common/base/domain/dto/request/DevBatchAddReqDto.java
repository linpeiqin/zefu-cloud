package com.zefu.common.base.domain.dto.request;

import lombok.Data;


@Data
public class DevBatchAddReqDto {
    Integer number;
    String productCode = "";
    String gwDevCode = "";
    String deviceName = "";
}
