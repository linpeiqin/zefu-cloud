package com.zefu.common.base.metadata;

import lombok.Getter;


@Getter
public enum EventTypeEnum {
    INFO("INFO", "通知"),
    WARN("WARN", "警告"),
    FAULT("FAULT", "故障");
    String code;
    String msg;

    EventTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static EventTypeEnum explain(String code){
        for(EventTypeEnum item:EventTypeEnum.values()){
            if(item.code.equals(code)){
                return item;
            }
        }
        return null;
    }
}
