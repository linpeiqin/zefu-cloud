package com.zefu.common.base.domain.gateway.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceActiveMqBo {
    String deviceCode;
    Boolean active;
    String host;
    Integer port;
    Date timestamp;
}
