package com.zefu.common.base.domain.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Data
public class LoginReqDto implements Serializable {
    private static final long serialVersionUID = 2103261624259751987L;
    @Size(min = 1, max = 20, message = "最大长度20")
    String userName;
    @NotNull(message = "密码不能为空")
    String password;
    /**图形验证码内容*/
    String capture;
    /**图形验证码序列号*/
    String captureNo;
}
