package com.zefu.business.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zefu.business.storage.EsMessage;
import lombok.Data;

import java.util.Date;


@Data
public class DeviceRtHistoryResDto extends EsMessage {
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8" )
    Date   replyTime;
    Integer status;
    String replyMessage;
}
