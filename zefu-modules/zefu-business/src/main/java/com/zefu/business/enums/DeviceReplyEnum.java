package com.zefu.business.enums;

import lombok.Getter;

@Getter
public enum DeviceReplyEnum {
    SUCCESS(200, "成功"),
    FAIL(201, "失败"),
    UNKNOWN(202, "未知状态"),
    NORELY(203, "未收到回执");
    int code;
    String msg;

    DeviceReplyEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
