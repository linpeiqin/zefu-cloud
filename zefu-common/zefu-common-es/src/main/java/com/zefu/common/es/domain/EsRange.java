package com.zefu.common.es.domain;

import lombok.Data;

@Data
public class EsRange<M,N> {
    M max;
    N min;
    String field;
}
