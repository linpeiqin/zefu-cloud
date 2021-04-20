package com.zefu.common.base.enums;

import lombok.Getter;


@Getter
public enum DevTypeEnum {

    GATEWAY("GATEWAY", "网关设备"),
    SUB("SUB", "网关子设备"),
    DIRECT("DIRECT", "直连设备");
    String type;
    String msg;

    DevTypeEnum(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public static DevTypeEnum explain(String type){
        for(DevTypeEnum item:DevTypeEnum.values()){
            if(item.type.equals(type)){
                return item;
            }
        }
        return null;
    }
}
