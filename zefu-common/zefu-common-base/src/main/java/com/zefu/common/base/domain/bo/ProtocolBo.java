package com.zefu.common.base.domain.bo;

import lombok.Data;

import java.util.Date;


@Data
public class ProtocolBo {
    private Long id;

    private String protocolCode;

    private String protocolName;

    private String protocolHandle;

    private String protocolFileUrl;

    private Integer protocolType;

    private String jarSign;

    private Date createTime;

    private Date updateTime;

    private Integer protocolStatus;

    private Integer delFlag;

}
