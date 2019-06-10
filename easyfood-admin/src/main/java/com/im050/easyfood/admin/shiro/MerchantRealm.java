package com.im050.easyfood.admin.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.im050.easyfood.admin.constant.SessionConstants;
import com.im050.easyfood.common.constant.ColumnConstants;
import com.im050.easyfood.common.entity.Merchant;
import com.im050.easyfood.common.entity.MerchantPermission;
import com.im050.easyfood.common.entity.MerchantRole;
import com.im050.easyfood.common.exception.MerchantNotFound;
import com.im050.easyfood.common.service.MerchantRolePermissionService;
import com.im050.easyfood.common.service.MerchantRoleService;
import com.im050.easyfood.common.service.MerchantService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MerchantRealm extends AuthorizingRealm {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private MerchantRoleService merchantRoleService;

    @Autowired
    private MerchantRolePermissionService merchantRolePermissionService;

    private static Logger log = LoggerFactory.getLogger(MerchantRealm.class);

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = null;
        try {
            authorizationInfo = new SimpleAuthorizationInfo();
            Merchant merchant = merchantService.findByUsername(username);
            MerchantRole merchantRole = merchantRoleService.getOne((new QueryWrapper<MerchantRole>().eq(ColumnConstants.MERCHANT_ID, merchant.getId())));
            authorizationInfo.addRole(merchantRole.getName());
            List<MerchantPermission> list = merchantRolePermissionService.getPermissionByRole(merchant.getRoleId());

            for (MerchantPermission p : list) {
                authorizationInfo.addStringPermission(p.getPermissionCode());
            }
        } catch (Exception e) {
            log.error("Get authorization info error: {}", e.getMessage());
            e.printStackTrace();
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        Merchant merchant = merchantService.findByUsername(username);
        if (merchant == null) {
            throw new MerchantNotFound();
        }

        // put merchant info into session cache.
        SecurityUtils.getSubject().getSession().setAttribute(SessionConstants.MERCHANT, merchant);

        // set session timeout
        SecurityUtils.getSubject().getSession().setTimeout(1000 * 60 * 60 * 24 * 7);

        return new SimpleAuthenticationInfo(
                merchant.getUsername(), //用户名
                merchant.getPassword(), //密码
                getName()
        );
    }
}
