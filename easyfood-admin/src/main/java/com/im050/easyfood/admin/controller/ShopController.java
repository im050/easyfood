package com.im050.easyfood.admin.controller;

import com.im050.easyfood.common.entity.Shop;
import com.im050.easyfood.common.utils.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop")
public class ShopController {


    @GetMapping("/redisTest")
    public Response redisTest() {
        return Response.success();
    }

    @GetMapping("/add")
    public Response add(@RequestBody Shop shop) {
        shop.insert();
        return Response.success();
    }
}
