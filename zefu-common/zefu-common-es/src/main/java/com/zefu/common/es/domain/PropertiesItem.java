package com.zefu.common.es.domain;

import lombok.Data;


@Data
public class PropertiesItem {
    String type;
    String format;
    /**
     * not_analyzed=>不索引
     *
     * */
    String index;
}
