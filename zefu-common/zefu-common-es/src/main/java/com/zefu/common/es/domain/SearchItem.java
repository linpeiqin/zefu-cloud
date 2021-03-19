package com.zefu.common.es.domain;

import com.zefu.common.es.config.enums.KeyMatchTypeEnum;
import lombok.Data;


@Data
public class SearchItem {
    String key;
    Object value;
    KeyMatchTypeEnum matchType;
}
