package com.im050.easyfood.admin.controller;

import com.im050.easyfood.admin.utils.Session;
import com.im050.easyfood.common.entity.Merchant;
import com.im050.easyfood.common.utils.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "获取商户信息")
@RequestMapping("/merchant")
@RestController
public class MerchantController {
    @ApiOperation("获取商户信息")
    @GetMapping("/info")
    public Response info() {
        Merchant merchant = Session.getMerchant();
        return Response.success(merchant);
    }
}
