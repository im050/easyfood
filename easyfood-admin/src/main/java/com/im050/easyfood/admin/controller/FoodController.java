package com.im050.easyfood.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.im050.easyfood.admin.annotation.ShopGuard;
import com.im050.easyfood.common.constant.ColumnConstants;
import com.im050.easyfood.common.entity.Food;
import com.im050.easyfood.common.exception.HttpException;
import com.im050.easyfood.common.service.AttrRelationshipService;
import com.im050.easyfood.common.service.FoodService;
import com.im050.easyfood.common.service.ShopService;
import com.im050.easyfood.common.utils.Tools;
import com.im050.easyfood.common.utils.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Api(description = "商品相关信息接口")
@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private AttrRelationshipService attrRelationshipService;

    @Value("${upload-path}")
    private String uploadPath;

    @Value("${static-url}")
    private String staticUrl;

    private Logger log = LoggerFactory.getLogger(FoodController.class);

    @ApiOperation("获取餐品信息")
    @GetMapping("/list")
    @RequiresPermissions("food:list")
    @ShopGuard
    public Response list(@RequestParam("shopId") Integer shopId, @RequestParam Integer menuId) {
        QueryWrapper<Food> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ColumnConstants.SHOP_ID, shopId);
        queryWrapper.eq(ColumnConstants.MENU_ID, menuId);
        List<Food> list = foodService.list(queryWrapper);
        return Response.success(list);
    }

    @ApiOperation("获取商品详情")
    @GetMapping("/detail")
    @RequiresPermissions("food:list")
    @ShopGuard
    public Response detail(@RequestParam("shopId") Integer shopId, @RequestParam("foodId") Integer foodId) {
        QueryWrapper<Food> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ColumnConstants.SHOP_ID, shopId);
        queryWrapper.eq(ColumnConstants.ID, foodId);
        Food food = foodService.getOne(queryWrapper);
        attrRelationshipService.injectAttr(food);
        return Response.success(food);
    }

    @ApiOperation("添加商品")
    @PostMapping("/add")
    @RequiresPermissions("food:add")
    @ShopGuard
    public Response add(@RequestBody Food food) {
        if (!shopService.hasMenu(food.getShopId(), food.getMenuId())) {
            return Response.error();
        }
        try {
            foodService.addFoodWithAttrs(food);
        } catch (HttpException e) {
            log.error("Add food failed: {}", e);
            return Response.error();
        }
        return Response.success(food.getId());
    }

    @ApiOperation("编辑商品")
    @PostMapping("/edit")
    @RequiresPermissions("food:edit")
    @ShopGuard
    public Response edit(@RequestBody Food food) {
        if (!shopService.hasMenu(food.getShopId(), food.getMenuId())) {
            return Response.error();
        }
        foodService.editFoodWithAttrs(food);
        return Response.success(food.getId());
    }

    @ApiOperation("上传接口")
    @PostMapping("/upload")
    @RequiresPermissions("food:edit")
    public Response upload(@RequestParam("file") MultipartFile file) {
        String fileName = "";
        try {
            fileName = Tools.saveFile(file, this.uploadPath);
        } catch (IOException e) {
            return Response.error();
        }
        UploadDataInfo ui = new UploadDataInfo();
        ui.setFileName(fileName).setUrl(staticUrl.concat(fileName));
        return Response.success(ui);
    }
}

@Data
@Accessors(chain = true)
class UploadDataInfo {
    private String fileName;
    private String url;
}