package com.im050.easyfood.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.im050.easyfood.admin.entity.FoodAdminVO;
import com.im050.easyfood.admin.utils.Session;
import com.im050.easyfood.common.constant.ColumnConstants;
import com.im050.easyfood.common.entity.Food;
import com.im050.easyfood.common.entity.Merchant;
import com.im050.easyfood.common.entity.view.FoodVO;
import com.im050.easyfood.common.service.FoodService;
import com.im050.easyfood.common.service.ShopService;
import com.im050.easyfood.common.utils.Tools;
import com.im050.easyfood.common.utils.response.Response;
import com.im050.easyfood.common.utils.response.ResponseCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
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

    @Value("${upload-path}")
    private String uploadPath;

    @Value("${static-url}")
    private String staticUrl;

    @ApiOperation("获取餐品信息")
    @GetMapping("/list")
    @RequiresPermissions("food:list")
    public Response list(@RequestParam("shopId") Integer shopId, @RequestParam Integer menuId) {
        Merchant merchant = Session.getMerchant();
        if (!shopService.checkShopOwner(shopId, merchant.getId())) {
            return Response.error(ResponseCode.SHOP_OWNER_ERROR);
        }
        QueryWrapper<Food> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ColumnConstants.SHOP_ID, shopId);
        queryWrapper.eq(ColumnConstants.MENU_ID, menuId);
        List<Food> list = foodService.list(queryWrapper);
        return Response.success(list);
    }

    @ApiOperation("获取商品详情")
    @GetMapping("/detail")
    @RequiresPermissions("food:list")
    public Response detail(@RequestParam("shopId") Integer shopId, @RequestParam("foodId") Integer foodId) {
        Merchant merchant = Session.getMerchant();
        if (!shopService.checkShopOwner(shopId, merchant.getId())) {
            return Response.error(ResponseCode.SHOP_OWNER_ERROR);
        }
        QueryWrapper<Food> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ColumnConstants.SHOP_ID, shopId);
        queryWrapper.eq(ColumnConstants.ID, foodId);
        Food food = foodService.getOne(queryWrapper);
        FoodAdminVO foodAdminVO = new FoodAdminVO();
        BeanUtils.copyProperties(food, foodAdminVO);
        return Response.success(foodAdminVO);
    }

    @ApiOperation("添加商品")
    @PostMapping("/add")
    @RequiresPermissions("food:add")
    public Response add(@RequestBody FoodAdminVO foodAdminVO) {
        Merchant merchant = Session.getMerchant();
        if (!shopService.checkShopOwner(foodAdminVO.getShopId(), merchant.getId())) {
            return Response.error(ResponseCode.SHOP_OWNER_ERROR);
        }
        if (!shopService.hasMenu(foodAdminVO.getShopId(), foodAdminVO.getMenuId())) {
            return Response.error();
        }
        Food food = new Food();
        BeanUtils.copyProperties(foodAdminVO, food);
        food.insert();
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