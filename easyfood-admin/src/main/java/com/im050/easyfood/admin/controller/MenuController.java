package com.im050.easyfood.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.im050.easyfood.admin.utils.Session;
import com.im050.easyfood.common.constant.ColumnConstants;
import com.im050.easyfood.common.entity.Menu;
import com.im050.easyfood.common.entity.Merchant;
import com.im050.easyfood.common.service.MenuService;
import com.im050.easyfood.common.service.ShopService;
import com.im050.easyfood.common.utils.response.Response;
import com.im050.easyfood.common.utils.response.ResponseCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "菜单相关接口")
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    ShopService shopService;

    @Autowired
    MenuService menuService;

    @RequiresPermissions("menu:add")
    @ApiOperation("添加菜单")
    @PostMapping("/add")
    public Response add(@RequestBody Menu menu) {
        Merchant merchant = Session.getMerchant();
        Integer shopId = menu.getShopId();
        if (!shopService.checkShopOwner(shopId, merchant.getId())) {
            return Response.error(ResponseCode.SHOP_OWNER_ERROR);
        }
        if (!menu.insert()) {
            return Response.error();
        }
        return Response.success(menu);
    }

    @ApiOperation("菜单列表")
    @GetMapping("/list")
    public Response list(@RequestParam("shopId") Integer shopId) {
        Merchant merchant = Session.getMerchant();
        if (!shopService.checkShopOwner(shopId, merchant.getId())) {
            return Response.error(ResponseCode.SHOP_OWNER_ERROR);
        }
        List<Menu> menus = menuService.list(new QueryWrapper<Menu>().eq(ColumnConstants.SHOP_ID, shopId).orderByAsc("sort"));
        return Response.success(menus);
    }

}
