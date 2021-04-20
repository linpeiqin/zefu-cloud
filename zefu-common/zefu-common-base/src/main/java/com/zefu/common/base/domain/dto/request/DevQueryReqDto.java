package com.zefu.common.base.domain.dto.request;

import lombok.Data;


@Data
public class DevQueryReqDto {
    String deviceName;
    String gwDevCode;
    String deviceCode;
    String productCode;
    Integer enableStatus;
    Integer activeStatus;
    String  nodeType;
    /**
     * 是否查询的是网关子设备
     *
     * */
    Boolean subDevQuery = false;
}
