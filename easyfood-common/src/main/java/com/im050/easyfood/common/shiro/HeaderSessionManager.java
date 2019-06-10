package com.im050.easyfood.common.shiro;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.Base64;

public class HeaderSessionManager extends DefaultWebSessionManager {
    private static final String TOKEN_NAME = "X-EasyFood-Token";

    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    public HeaderSessionManager() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String base64Token = WebUtils.toHttp(request).getHeader(TOKEN_NAME);
        if (!StringUtils.isEmpty(base64Token)) {
            byte[] token = Base64.getDecoder().decode(base64Token);
            String originToken = new String(token);
            String[] decodeToken = StringUtils.split(originToken, "|");
            String id = decodeToken != null && decodeToken.length > 0 ? decodeToken[0] : null;
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        } else {
            return super.getSessionId(request, response);
        }
    }
}
