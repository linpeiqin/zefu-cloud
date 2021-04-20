package com.zefu.common.base.domain.dto.request.device;

import lombok.Data;

import java.util.List;


@Data
public class BatchStatusReqDto {
    /**list内容是主键ID*/
    List<Long> list;
    String     type;
}
