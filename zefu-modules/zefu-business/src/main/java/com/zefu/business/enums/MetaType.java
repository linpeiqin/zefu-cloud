package com.zefu.business.enums;

import com.zefu.common.es.config.enums.EsMetaType;
import lombok.Getter;


@Getter
public enum MetaType {
    STRING("text", EsMetaType.KEYWORD, "字符串|text", "text", "基本类型"),
    INTEGER("int", EsMetaType.INTEGER,"整型|int" , "num", "基本类型"),
    BOOLEAN("boolean", EsMetaType.BOOLEAN, "布尔类型|boolean", "boolean", "基本类型"),
    DATE("date", EsMetaType.DATE, "日期类型|date", "date", "基本类型"),
    FLOAT("float", EsMetaType.FLOAT, "单精度型浮点|float", "num", "基本类型"),
    DOUBLE("double", EsMetaType.DOUBLE, "双精度型浮点|double", "num", "基本类型"),
    LONG("long", EsMetaType.LONG, "长整型|long", "num", "基本类型"),
    STRUCT("struct", EsMetaType.OBJECT, "结构体|struct", "struct", "复合类型");
    String       code;
    EsMetaType esMetaType;
    String       msg;
    String type;
    String group;

    MetaType(String code, EsMetaType esMetaType, String msg, String type, String group) {
        this.code = code;
        this.esMetaType = esMetaType;
        this.msg = msg;
        this.type = type;
        this.group = group;
    }

    public static MetaType explain(String code){
        for(MetaType item:MetaType.values()){
            if(item.code.equals(code)){
                return item;
            }
        }
        return null;
    }
}
