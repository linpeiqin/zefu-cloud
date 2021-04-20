package com.zefu.common.base.domain.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class PageReqDto<T> implements Serializable {

    private static final long serialVersionUID = -3214634852095029897L;
    private int pageNo = 1;
    private int          limit = 20;
    private              T paramData;

}

