package com.zefu.common.base.exception;

import com.zefu.common.base.enums.ErrorEnum;
import lombok.Data;


@Data
public class GException extends RuntimeException {
    /**
     * 错误码
     */
    Integer errorCode;
    /**
     * 错误描述
     */
    String msg;
    /**
     * 附加错误描述
     */
    String extraMsg;
    /**
     * 捕获的异常
     */
    Throwable t;

    /**
     * 错误信息模板
     */
    private final static String MSG_TEMPLATE = "错误码:%d, 描述:%s, 异常信息:%s";

    public GException(Throwable t) {
        super(getMessage(-1, t.getMessage(), ""), t);
        this.t = t;
    }

    public GException(Integer errorCode, String msg, String extraMsg, Throwable t) {
        super(getMessage(errorCode, msg, extraMsg), t);
        this.errorCode = errorCode;
        this.msg = msg;
        this.extraMsg = extraMsg;
        this.t = t;
    }
    public GException(ErrorEnum errorEnum, String extraMsg, Throwable t) {
        super(getMessage(errorEnum.getCode(), errorEnum.getMsg(), extraMsg), t);
        this.errorCode = errorEnum.getCode();
        this.msg = errorEnum.getMsg();
        this.extraMsg = extraMsg;
        this.t = t;
    }

    public GException(Integer errorCode, String msg) {
        super(getMessage(errorCode, msg, ""), null);
        this.errorCode = errorCode;
        this.msg = msg;
        this.t = t;
    }

    public GException(Integer errorCode, String msg, String extraMsg) {
        super(getMessage(errorCode, msg, extraMsg), null);
        this.errorCode = errorCode;
        this.msg = msg;
        this.t = t;
    }
    public GException(ErrorEnum errorEnum) {
        super(getMessage(errorEnum.getCode(), errorEnum.getMsg(), ""), null);
        this.errorCode = errorEnum.getCode();
        this.msg = errorEnum.getMsg();
        this.t = t;
    }

    public static GException genException(ErrorEnum errorEnum){
        GException exception = new GException(errorEnum);
        return exception;
    }

    public static GException genException(ErrorEnum errorEnum, String extraMessage){
        GException exception = new GException(errorEnum);
        exception.setExtraMsg(extraMessage);
        return exception;
    }

    /**
     * 根据错误信息模板返回错误信息
     *
     * @param errorCode
     * @param extraMessage
     * @return
     */
    private static String getMessage(Integer errorCode, String msg, String extraMessage) {
        extraMessage = (null != extraMessage) ? extraMessage : "";
        try {
            return String.format(MSG_TEMPLATE, errorCode, msg, extraMessage);
        } catch (Exception e) {
            return "";
        }
    }
}
