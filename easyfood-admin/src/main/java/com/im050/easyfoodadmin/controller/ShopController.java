package com.im050.easyfoodadmin.controller;

import com.im050.easyfoodcommon.entity.Shop;
import com.im050.easyfoodcommon.utils.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/shop")
public class ShopController {
    @GetMapping("/test")
    public Response test() {
        Shop shop = new Shop();
        shop.setAddress("北京").setDescription("测试").setLatitude(BigDecimal.ZERO)
                .setLongitude(BigDecimal.ZERO)
                .setMerchantId(1)
                .setNotice("测试商铺");
        shop.insert();
        return Response.success();
    }
}
