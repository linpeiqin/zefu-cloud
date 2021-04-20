package com.zefu.common.base.domain.gateway.mq;


import com.zefu.common.base.domain.dto.response.device.DevicePageResDto;
import com.zefu.common.base.domain.message.DeviceDownMessage;
import lombok.Data;


@Data
public class PropertyReaderMessageBo {
    DevicePageResDto devicePageResDto;
    DeviceDownMessage deviceDownMessage;
}
