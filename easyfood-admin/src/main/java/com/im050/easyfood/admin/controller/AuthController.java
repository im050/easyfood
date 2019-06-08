package com.im050.easyfood.admin.controller;

import com.im050.easyfood.common.exception.MerchantNotFound;
import com.im050.easyfood.common.service.MerchantService;
import com.im050.easyfood.common.utils.Tools;
import com.im050.easyfood.common.utils.response.Response;
import com.im050.easyfood.common.utils.response.ResponseCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@Api(description = "后台登录")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    MerchantService merchantService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public Response login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, Tools.md5(password));
        try {
            subject.login(token);
        } catch (MerchantNotFound e) {
            e.printStackTrace();
        } catch (IncorrectCredentialsException e) {
            return Response.error(ResponseCode.USERNAME_NOT_MATCH);
        } catch (AuthenticationException e) {
            log.error("Authentication error: {}", e);
            return Response.error(ResponseCode.USERNAME_NOT_MATCH);
        }
        return Response.success(subject.getSession().getId());
    }

    @ApiOperation("退出")
    @RequestMapping("/logout")
    public Response logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Response.success();
    }
}
