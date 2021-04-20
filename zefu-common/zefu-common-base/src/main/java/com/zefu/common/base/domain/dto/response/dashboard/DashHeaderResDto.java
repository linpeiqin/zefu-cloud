package com.zefu.common.base.domain.dto.response.dashboard;

import lombok.Data;


@Data
public class DashHeaderResDto {
    Integer deviceCount;
    Integer deviceActive;
    Integer deviceEnable;
    Integer msgCount;
}
