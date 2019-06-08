package com.im050.easyfood.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.im050.easyfood.common.constant.ColumnConstants;
import com.im050.easyfood.common.entity.Food;
import com.im050.easyfood.common.entity.Menu;
import com.im050.easyfood.common.entity.view.FoodVO;
import com.im050.easyfood.common.entity.view.MenuVO;
import com.im050.easyfood.common.service.*;
import com.im050.easyfood.common.utils.Tools;
import com.im050.easyfood.common.entity.Shop;
import com.im050.easyfood.common.dao.ShopDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linyulin
 * @since 2019-06-01
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopDao, Shop> implements ShopService {

    @Autowired
    MenuService menuService;

    @Autowired
    AttrService attrService;

    @Autowired
    FoodService foodService;

    @Autowired
    AttrRelationshipService attrRelationshipService;

    @Autowired
    ShopDao shopDao;

    @Override
    public List<Shop> getMerchantShop(Integer merchantId) {
        return shopDao.selectMerchantShop(merchantId);
    }

    @Override
    public boolean checkShopOwner(Integer shopId, Integer merchantId) {
        List<Shop> shops = getMerchantShop(merchantId);
        for (Shop shop : shops) {
            if (shop.getId().equals(shopId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<MenuVO> getAllFoodsWithMenu(Integer shopId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(ColumnConstants.SHOP_ID, shopId);
        List<Menu> menus = menuService.list(queryWrapper);
        List<MenuVO> menuVOS = Tools.changeToVo(menus, MenuVO.class);

        QueryWrapper foodQueryWrapper = new QueryWrapper();
        foodQueryWrapper.in(ColumnConstants.MENU_ID, menus.stream().map(Menu::getId).collect(Collectors.toList()));
        List<Food> foods = foodService.list(foodQueryWrapper);
        List<FoodVO> foodVOS = Tools.changeToVo(foods, FoodVO.class);
        Map<Integer, List<FoodVO>> foodsMenuGroup = foodVOS.stream().collect(Collectors.groupingBy(FoodVO::getMenuId));
        System.out.println(foodVOS);

        menuVOS.forEach(menu -> {
            menu.setFoods(foodsMenuGroup.get(menu.getId()));
        });

        attrRelationshipService.injectAttr(foodVOS);

        //
//        QueryWrapper attrQueryWrapper = new QueryWrapper();
//        attrQueryWrapper.in(ColumnConstants.FOOD_ID, foods.stream().map(Food::getId).collect(Collectors.toList()));
//        List<Attr> attrs = attrService.list(attrQueryWrapper);
//        List<AttrVO> attrVOS = Tools.changeToVo(attrs, AttrVO.class);

        return menuVOS;
    }
}
