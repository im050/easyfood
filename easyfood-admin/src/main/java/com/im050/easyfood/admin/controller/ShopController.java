package com.im050.easyfood.admin.controller;

import com.im050.easyfood.common.utils.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@Api(description = "商店相关API")
@RestController
@RequestMapping("/shop")
public class ShopController {

    @ApiOperation("查看商店列表")
    @RequiresPermissions("shop:list")
    @GetMapping("/list")
    public Response list() {
        return Response.success();
    }

    @ApiOperation("申请新的商店")
    @RequiresPermissions("shop:add")
    @PostMapping("/apply")
    public Response apply() {
        return Response.success();
    }

}
