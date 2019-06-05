package com.im050.easyfood.common.advice;

import com.im050.easyfood.common.utils.response.Response;
import com.im050.easyfood.common.utils.response.ResponseCode;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Response exceptionHandler(Exception exception) {
        log.error("global error: {}", exception);
        return Response.error();
    }

    @ResponseBody
    @ExceptionHandler(value = AuthorizationException.class)
    public Response authorizationException(AuthorizationException exception) {
        return Response.error(ResponseCode.UNAUTHORIZED);
    }
}
