package com.im050.easyfood.common.shiro;

import com.im050.easyfood.common.utils.TokenTool;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Base64;

public class HeaderSessionManager extends DefaultWebSessionManager {
    public static final String TOKEN_NAME = "X-EasyFood-Token";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public HeaderSessionManager() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String base64Token = WebUtils.toHttp(request).getHeader(TOKEN_NAME);
        if (!StringUtils.isEmpty(base64Token)) {
            String id = TokenTool.build(base64Token).getToken();
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        } else {
            return super.getSessionId(request, response);
        }
    }
}
