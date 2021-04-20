package com.zefu.common.base.domain.dto.response.dashboard;

import lombok.Data;

import java.util.List;


@Data
public class DashLineResDto {
    List<String> dates;
    List<Long> count;
}
