package com.zefu.common.base.domain.dto.request.product;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class ReleasePropReqDto {
    @NotNull(message = "产品编码不能为空")
    String productCode;
    @NotNull(message = "属性类型不能为空")
    String funcType;
}
