package com.im050.easyfood.common.shiro;

import com.alibaba.fastjson.JSONObject;
import com.im050.easyfood.common.utils.response.Response;
import com.im050.easyfood.common.utils.response.ResponseCode;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class AuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        PrintWriter out = null;
        HttpServletResponse res = (HttpServletResponse) response;
        try {
            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json");
            out = response.getWriter();
            Response errorResponse = Response.error(ResponseCode.UNLOGIN);
            out.println(new JSONObject(errorResponse));
        } catch (Exception ignored) {

        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
        return false;
    }
}