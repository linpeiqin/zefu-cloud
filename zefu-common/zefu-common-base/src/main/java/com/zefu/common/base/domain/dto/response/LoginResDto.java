package com.zefu.common.base.domain.dto.response;

import lombok.Data;

import java.io.Serializable;


@Data
public class LoginResDto implements Serializable {
    private static final long serialVersionUID = 7924126418546482089L;
    String token;
    String userNick;
    String userName;
    Integer roleType;
    String  roleTypeName;
}
