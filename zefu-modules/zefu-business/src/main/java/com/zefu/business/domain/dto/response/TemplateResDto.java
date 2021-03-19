package com.zefu.business.domain.dto.response;


import java.util.Map;
import lombok.Data;

@Data
public class TemplateResDto {
    /**请求数据示例*/
    Map<String,Object> demoData;
    /**请求数据模版*/
    Map<String, Object> template;
}
