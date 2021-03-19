package com.zefu.common.es.config.enums;

import lombok.Getter;


@Getter
public enum EsMetaType {
    KEYWORD("keyword", ""),
    TEXT("text", ""),
    STRING("keyword", ""),
    LONG("long",""),
    DOUBLE("double", ""),
    FLOAT("float", ""),
    DATE("date", ""),
    BOOLEAN("boolean",""),
    INTEGER("integer",""),
    GEOPOINT("geo_point ", ""),
    OBJECT("object", ""),
    UNKNOWN("unknown ", "");
    String type;
    String msg;

    EsMetaType(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }
}
