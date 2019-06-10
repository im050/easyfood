package com.im050.easyfood.common.exception;

import com.im050.easyfood.common.utils.response.ResponseCode;

import java.text.MessageFormat;

public class HttpException extends RuntimeException {
    private Integer code;

    private Object[] params;

    public HttpException() {
        super();
    }

    public HttpException(String message) {
        super(message);
        this.code = ResponseCode.ERROR.getCode();
    }

    public HttpException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
    }

    public HttpException(ResponseCode responseCode, Object ...params) {
        super(MessageFormat.format(responseCode.getMessage(), params));
        this.code = responseCode.getCode();
        this.params = params;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
