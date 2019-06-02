package com.im050.easyfoodcommon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.im050.easyfoodcommon.entity.view.FoodVO;
import com.im050.easyfoodcommon.entity.view.MenuVO;
import com.im050.easyfoodcommon.service.*;
import com.im050.easyfoodcommon.constant.ColumnConstants;
import com.im050.easyfoodcommon.entity.Food;
import com.im050.easyfoodcommon.entity.Menu;
import com.im050.easyfoodcommon.entity.Shop;
import com.im050.easyfoodcommon.dao.ShopDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.im050.easyfoodcommon.utils.Tools;
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
