package com.im050.easyfood.common.service;

import com.im050.easyfood.common.entity.Shop;
import com.im050.easyfood.common.entity.view.MenuVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linyulin
 * @since 2019-06-01
 */
public interface ShopService extends IService<Shop> {


    List<MenuVO> getAllFoodsWithMenu(Integer shopId);
}