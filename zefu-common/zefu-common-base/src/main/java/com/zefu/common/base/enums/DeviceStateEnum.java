package com.zefu.common.base.enums;

import lombok.Getter;


@Getter
public enum DeviceStateEnum {
    TOTAL(1, "全部设备"),
    ACTIVE(2, "在线设备");
    int type;
    String msg;

    DeviceStateEnum(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public static DeviceStateEnum explain(int type){
        for(DeviceStateEnum stateEnum: DeviceStateEnum.values()){
            if(stateEnum.type == type){
                return stateEnum;
            }
        }
        return null;
    }
}
