package com.im050.easyfood.common.utils.response;

public enum ResponseCode {
    SUCCESS(200, "success"),
    ERROR(500, "系统错误"),
    USERNAME_NOT_MATCH(401, "用户名或密码不正确"),
    UNAUTHORIZED(401, "无权限访问"),
    UNLOGIN(2, "未登录，请先登录"),
    SHOP_OWNER_ERROR(1000, "无权操作该商店信息");

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
