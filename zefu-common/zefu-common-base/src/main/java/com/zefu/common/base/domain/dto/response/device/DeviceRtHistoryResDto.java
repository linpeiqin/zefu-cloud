package com.zefu.common.base.domain.dto.response.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zefu.common.base.domain.storage.EsMessage;
import lombok.Data;

import java.util.Date;


@Data
public class DeviceRtHistoryResDto extends EsMessage {
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8" )
    Date   replyTime;
    Integer status;
    String replyMessage;
}
