package com.im050.easyfood.common.utils.response;

import java.util.HashMap;


public class Response extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    private static String CODE = "code";
    private static String MSG = "message";
    private static String DATA = "data";


    public Response() {
        put(CODE, ResponseCode.SUCCESS.getCode());
    }

    public Response(Integer code, String message, Object... objects) {
        put(CODE, code);
        put(MSG, message);
        if (objects.length > 0) {
            put(DATA, objects[0]);
        }
    }

    public Response(ResponseCode responseCode, Object object) {
        this(responseCode.getCode(), responseCode.getMessage(), object);
    }

    public static Response success(Object data) {
        return new Response(ResponseCode.SUCCESS, data);
    }

    public static Response success() {
        return success(null);
    }

    public static Response error(ResponseCode responseCode) {
        return new Response(responseCode, null);
    }

    public static Response error() {
        return new Response(ResponseCode.ERROR, null);
    }

    public static Response error(Integer code, String message) {
        return new Response(code, message);
    }

}

