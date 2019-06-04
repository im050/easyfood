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

    public Response(ResponseCode responseCode, Object object) {
        put(CODE, responseCode.getCode());
        put(MSG, responseCode.getMessage());
        if (object != null) {
            put(DATA, object);
        }
    }

    public static Response success(Object data) {
        return new Response(ResponseCode.SUCCESS, data);
    }

    public static Response success() {
        return success(null);
    }

}

