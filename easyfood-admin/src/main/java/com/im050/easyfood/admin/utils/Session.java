package com.im050.easyfood.admin.utils;

import com.alibaba.fastjson.JSONObject;
import com.im050.easyfood.admin.constant.SessionConstants;
import com.im050.easyfood.common.entity.Merchant;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class Session {
    private static Logger logger = LoggerFactory.getLogger(Session.class);

    public static Merchant getMerchant() {
        try {
            return (Merchant) SecurityUtils.getSubject().getSession().getAttribute(SessionConstants.MERCHANT);
        } catch (Exception e) {
            logger.error("获取用户信息一次{}", e);
        }
        return null;

    }

    public static Object getAttribute(Object o) {
        return SecurityUtils.getSubject().getSession().getAttribute(o);
    }

    public static void setAttribute(Object o, Object o1) {
        SecurityUtils.getSubject().getSession().setAttribute(o, o1);
    }

    public static Serializable getSessionId() {
        return SecurityUtils.getSubject().getSession().getId();
    }

    public static void setMerchant(Merchant merchant) {
        setAttribute(SessionConstants.MERCHANT, merchant);
    }

}
