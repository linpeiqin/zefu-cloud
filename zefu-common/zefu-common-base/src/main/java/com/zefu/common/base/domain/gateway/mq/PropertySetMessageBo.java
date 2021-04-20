package com.zefu.common.base.domain.gateway.mq;

import com.alibaba.fastjson.JSONObject;
import com.zefu.common.base.metadata.ProductFuncTypeEnum;
import lombok.Data;

import java.util.Date;


@Data
public class PropertySetMessageBo {
    String     deviceCode;
    /**下发属性标识符 */
    String              identifier;
    JSONObject command;
    String              dataType;
    String     productCode;
    Date       currTime = new Date();
   // IBaseProtocol baseProtocolService;
    String topic;
    String protocolCode;
    ProductFuncTypeEnum funcType;
    /**
     * 因为要分布式，所以messageId生成放到调用接口的时候生成，后面多个消费者根据messageId来判断是不是要存数据到es
     * */
    String messageId;
}
