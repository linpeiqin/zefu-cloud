package com.zefu.common.base.domain.dto.request.product;

import lombok.Data;


@Data
public class ProductQueryReqDto {
    String nodeType;
    String productCode;
    String productName;
}
