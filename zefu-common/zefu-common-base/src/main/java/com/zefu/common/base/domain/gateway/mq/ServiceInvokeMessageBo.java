package com.zefu.common.base.domain.gateway.mq;

import com.alibaba.fastjson.JSONObject;
import com.zefu.common.base.metadata.ProductFuncTypeEnum;
import lombok.Data;

import java.util.Date;


@Data
public class ServiceInvokeMessageBo {
    String     deviceCode;
    /**下发服务标识符 */
    String              identifier;
    JSONObject command;
    String              dataType;
    String     productCode;
    Date       currTime = new Date();
   // IBaseProtocol baseProtocolService;
    String topic;
    ProductFuncTypeEnum funcType;
    String protocolCode;
    String messageId;

}
