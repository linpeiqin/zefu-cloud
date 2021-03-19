package com.zefu.common.es.config.enums;

import lombok.Getter;

@Getter
public enum KeyMatchTypeEnum {
    EXIST("exist", "需要精准比较值"),
    NOT_EXIST("exist", "只需要判断值不为null");
    String type;
    String msg;

    KeyMatchTypeEnum(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public static KeyMatchTypeEnum explain(String type){
        for(KeyMatchTypeEnum item:KeyMatchTypeEnum.values()){
            if(item.type.equals(type)){
                return item;
            }
        }
        return KeyMatchTypeEnum.EXIST;
    }
}
