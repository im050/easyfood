package com.im050.easyfood.admin.controller;

import com.im050.easyfood.admin.annotation.ShopGuard;
import com.im050.easyfood.admin.entity.MerchantAdminVO;
import com.im050.easyfood.admin.utils.Session;
import com.im050.easyfood.common.entity.Merchant;
import com.im050.easyfood.common.entity.Shop;
import com.im050.easyfood.common.service.ShopService;
import com.im050.easyfood.common.utils.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description = "获取商户信息")
@RequestMapping("/merchant")
@RestController
public class MerchantController {

    @Autowired
    private ShopService shopService;

    @ApiOperation("获取商户信息")
    @GetMapping("/info")
    public Response info() {
        Merchant merchant = Session.getMerchant();
        List<Shop> shops = shopService.getMerchantShop(merchant.getId());
        MerchantAdminVO merchantAdminVO = new MerchantAdminVO();
        BeanUtils.copyProperties(merchant, merchantAdminVO);
        merchantAdminVO.setShops(shops);
        return Response.success(merchantAdminVO);
    }
}
