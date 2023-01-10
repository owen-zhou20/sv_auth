package com.sv.common.result;

import lombok.Getter;

/**
 * 统一返回结果状态信息类
 *
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(20000,"Success!"),
    FAIL(20001, "Fail!"),
    SERVICE_ERROR(2012, "Service error!"),
    DATA_ERROR(204, "Data error!"),
    ILLEGAL_REQUEST(205, "Illegal request!"),
    REPEAT_SUBMIT(206, "Repeat submit!"),
    ARGUMENT_VALID_ERROR(210, "argument valid error!"),

    LOGIN_AUTH(208, "Please login!"),
    PERMISSION(209, "Please get permission!"),
    ACCOUNT_ERROR(214, "This username is not correct!"),
    PASSWORD_ERROR(215, "This password is not correct!"),
    LOGIN_MOBLE_ERROR( 216, "This username is not correct!"),
    ACCOUNT_STOP( 217, "This account is stop!"),
    NODE_ERROR( 218, "Can't delete this node because this node has sub-nodes!")
    ;

    private Integer code;

    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
