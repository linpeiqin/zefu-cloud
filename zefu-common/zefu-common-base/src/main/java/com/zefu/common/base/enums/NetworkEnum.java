package com.zefu.common.base.enums;

import com.zefu.common.base.exception.GException;
import lombok.Getter;


@Getter
public enum NetworkEnum {
    MQTT("MQTT", "MQTT协议"),
    UDP("UDP", "UDP");
    String type;
    String msg;

    NetworkEnum(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public static NetworkEnum explain(String type){
        for(NetworkEnum item:NetworkEnum.values()){
            if(item.type.equals(type)){
                return item;
            }
        }
        return null;
    }
    public static NetworkEnum explainMust(String type){
        for(NetworkEnum item:NetworkEnum.values()){
            if(item.type.equals(type)){
                return item;
            }
        }
        throw GException.genException(ErrorEnum.INVALID_NETWORK , type);
    }
}
