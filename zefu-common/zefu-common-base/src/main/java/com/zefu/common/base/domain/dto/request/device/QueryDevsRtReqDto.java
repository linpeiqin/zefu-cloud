package com.zefu.common.base.domain.dto.request.device;

import lombok.Data;

import java.util.List;


@Data
public class QueryDevsRtReqDto {
    List<String> deviceCodes;
    String productCode;
    String type;
}
