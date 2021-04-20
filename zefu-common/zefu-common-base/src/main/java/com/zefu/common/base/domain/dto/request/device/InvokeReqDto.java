package com.zefu.common.base.domain.dto.request.device;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
public class InvokeReqDto {
    @NotNull(message = "设备编码不能为空")
    String     deviceCode;
    /**下发服务标识符 */
    @NotNull(message = "标识符不能为空")
    String     identifier;
    /**消息体*/
    JSONObject command;
    String     dataType;
    @NotNull(message = "产品编码不能为空")
    String productCode;
    Date   timestamp = new Date();
}
