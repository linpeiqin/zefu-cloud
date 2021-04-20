package com.zefu.common.base.domain.gateway.direct.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
public class PropertyGetRequest {
    @NotNull(message = "设备编码不能为空")
    String     deviceCode;
    /**下发服务标识符 */
    @NotNull(message = "标识符不能为空")
    String     identifier;
    /**超时时间，单位秒，默认10s*/
    Long timeout = 10L;
    /**调用的时间*/
    Date timestamp = new Date();
}
