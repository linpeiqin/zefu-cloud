package com.zefu.common.base.metadata;

import lombok.Data;


@Data
public class MetaMsgTemplate {
    String code;
    String name;
    String desc;

    public MetaMsgTemplate(String code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;

    }
}
