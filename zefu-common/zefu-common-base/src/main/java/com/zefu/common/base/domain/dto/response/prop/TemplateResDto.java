package com.zefu.common.base.domain.dto.response.prop;

import lombok.Data;

import java.util.Map;


@Data
public class TemplateResDto {
    /**请求数据示例*/
    Map<String,Object> demoData;
    /**请求数据模版*/
    Map<String, Object> template;
}
