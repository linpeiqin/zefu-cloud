package com.zefu.common.base.domain.dto.response;

import lombok.Data;


@Data
public class DataTypeItemResDto {
    String code;
    String name;
    String desc;

    public DataTypeItemResDto(String code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }
}
