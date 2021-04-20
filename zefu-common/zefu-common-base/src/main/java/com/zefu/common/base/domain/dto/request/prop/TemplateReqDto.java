package com.zefu.common.base.domain.dto.request.prop;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TemplateReqDto {
    @NotNull(message = "产品不能为空")
    String productCode;
    String identifier;
    @NotNull(message = "功能类型不能为空")
    String funcType;
}
