package com.im050.easyfood.admin.shiro;

import com.im050.easyfood.common.entity.Merchant;
import com.im050.easyfood.common.service.MerchantService;
import com.im050.easyfood.common.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MerchantRealm extends AuthorizingRealm {

    @Autowired
    private MerchantService merchantService;
    private static Logger log = LoggerFactory.getLogger(MerchantRealm.class);

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        String username = (String) principalCollection.getPrimaryPrincipal();
//        SimpleAuthorizationInfo authorizationInfo = null;
//        try {
//            authorizationInfo = new SimpleAuthorizationInfo();
////            User user = userService.findByUsername(username);
//            Merchant merchant = merchantService.findByUsername(username);
//            authorizationInfo.addRole(user.getRole().getCode());
//            List<Permission> list = user.getRole().getPermissionList();
//            for(Permission p:list){
//                authorizationInfo.addStringPermission(p.getCode());
//            }
//        } catch (Exception e) {
//            log.error("授权错误{}", e.getMessage());
//            e.printStackTrace();
//        }
        //return authorizationInfo;
return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
