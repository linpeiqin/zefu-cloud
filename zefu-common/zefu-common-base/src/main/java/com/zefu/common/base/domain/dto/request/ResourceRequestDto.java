package com.zefu.common.base.domain.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class ResourceRequestDto {
    @Length(min = 1, max = 55, message = "编码长度[1,55]")
    private String resourceCode;
    @Length(min = 1, max = 40, message = "编码长度[1,40]")
    private String resourceName;
}
