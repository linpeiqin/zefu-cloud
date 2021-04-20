package com.zefu.common.base.metadata;

import lombok.Getter;


@Getter
public enum ProductFuncTypeEnum {
    PROP("PROP", "属性"),
    EVENT("EVENT", "事件"),
    SERVICE("SERVICE", "服务");
    String type;
    String msg;

    ProductFuncTypeEnum(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public static ProductFuncTypeEnum explain(String type){
        for(ProductFuncTypeEnum item:ProductFuncTypeEnum.values()){
            if(item.type.equals(type)){
                return item;
            }
        }
        return ProductFuncTypeEnum.PROP;
    }
}
