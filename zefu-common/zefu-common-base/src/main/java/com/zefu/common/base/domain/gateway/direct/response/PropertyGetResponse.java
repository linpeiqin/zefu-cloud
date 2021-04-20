package com.zefu.common.base.domain.gateway.direct.response;

import lombok.Data;


@Data
public class PropertyGetResponse {
    String     deviceCode;
    /**下发服务标识符 */
    String     identifier;
    /**属性值*/
    Object value;
}
