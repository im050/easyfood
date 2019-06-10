package com.im050.easyfood.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.im050.easyfood.admin.annotation.ShopGuard;
import com.im050.easyfood.admin.param.SortParam;
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
import org.springframework.transaction.annotation.Transactional;
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
    @ShopGuard
    public Response add(@RequestBody Menu menu) {
        menu.setId(null);
        if (!menu.insert()) {
            return Response.error();
        }
        return Response.success(menu);
    }

    @ApiOperation("菜单列表")
    @RequiresPermissions("menu:list")
    @GetMapping("/list")
    @ShopGuard
    public Response list(Integer shopId) {
        List<Menu> menus = menuService.list(new QueryWrapper<Menu>().eq(ColumnConstants.SHOP_ID, shopId).orderByAsc("sort"));
        return Response.success(menus);
    }


    @ApiOperation("菜单排序更新")
    @PostMapping("/sortUpdate")
    @RequiresPermissions("menu:edit")
    @Transactional
    @ShopGuard
    public Response sortUpdate(@RequestBody SortParam sortParam) {
        List<Integer> ids = sortParam.getIds();
        for (int i = 0; i < ids.size(); i++) {
            UpdateWrapper updateWrapper = new UpdateWrapper();
            Integer id = ids.get(i);
            updateWrapper.eq(ColumnConstants.ID, id);
            updateWrapper.eq(ColumnConstants.SHOP_ID, sortParam.getShopId());
            updateWrapper.set(ColumnConstants.SORT, i);
            menuService.update(updateWrapper);
        }
        return Response.success();
    }


    @ApiOperation("编辑分类")
    @PostMapping("/edit")
    @RequiresPermissions("menu:edit")
    @ShopGuard
    public Response edit(@RequestBody Menu menu) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq(ColumnConstants.ID, menu.getId());
        updateWrapper.eq(ColumnConstants.SHOP_ID, menu.getShopId()); //验证shopId是否对应
        updateWrapper.set(ColumnConstants.NAME, menu.getName());
        updateWrapper.set(ColumnConstants.DESCRIPTION, menu.getDescription());
        if (menuService.update(updateWrapper)) {
            return Response.success();
        }
        return Response.error();
    }

    @ApiOperation("删除分类")
    @PostMapping("/delete")
    @RequiresPermissions("menu:delete")
    @ShopGuard
    public Response delete(Integer shopId, @RequestParam("menuId") Integer id) {
        QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
        menuQueryWrapper.eq(ColumnConstants.SHOP_ID, shopId);
        menuQueryWrapper.eq(ColumnConstants.ID, id);
        if (menuService.remove(menuQueryWrapper)) {
            return Response.success();
        }
        return Response.error();
    }

}
