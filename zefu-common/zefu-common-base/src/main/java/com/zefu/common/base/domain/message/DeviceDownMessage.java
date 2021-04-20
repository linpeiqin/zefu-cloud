package com.zefu.common.base.domain.message;

import lombok.Data;


@Data
public class DeviceDownMessage {
    String messageId;
    /**时间戳，单位毫秒*/
    Long timestamp;
    /**消息体*/
    Object body;
    /**下发的指令,服务调用的时候就是服务标识符*/
    String identifier;
    String productCode;
    String deviceCode;
}
