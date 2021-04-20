package com.zefu.common.base.domain.gateway.mq;

import com.zefu.common.base.metadata.ProductFuncTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceUpMessageBo {
    String        productCode;
    //IBaseProtocol baseProtocolService;
    byte[]        sourceMsg;
    String topic;
    /**mqtt消息中的packetId*/
    Long packetId;
    String deviceCode;
    Date currTime = new Date();
    /**物模型类型*/
    ProductFuncTypeEnum funcType;
}
