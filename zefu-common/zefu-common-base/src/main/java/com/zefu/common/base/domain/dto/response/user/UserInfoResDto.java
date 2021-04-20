package com.zefu.common.base.domain.dto.response.user;

import lombok.Data;

import java.util.List;


@Data
public class UserInfoResDto {
    List<String> roles;
    String introduction;
    String avatar = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif";
    String name;

}
