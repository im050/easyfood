package com.im050.easyfood.common.utils.response;

public enum ResponseCode {
    SUCCESS(200, "success");

    private Integer code;
    private String message;

    ResponseCode(final Integer code, final String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
