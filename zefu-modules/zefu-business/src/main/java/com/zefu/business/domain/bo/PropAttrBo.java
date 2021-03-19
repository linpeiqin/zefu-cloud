package com.zefu.business.domain.bo;

import lombok.Data;


@Data
public class PropAttrBo {
    String dataType;
    String identifier;
    Long max;
    Long min;
    String unit;
    String unitName;
    String bool0;
    String bool1;
    Long length;
    Object data;
}
