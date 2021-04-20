package com.zefu.common.base.domain.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class CaptchResDto {
    /**
     * 图像验证码BASE64
     */
    @ApiModelProperty(value = "图片")
    private String img;
    /**
     * 图像验证码KEY
     */
    @ApiModelProperty(value = "图像验证码KEY")
    private String captchaToken;
}
