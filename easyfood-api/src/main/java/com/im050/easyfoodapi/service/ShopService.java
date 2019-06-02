package com.im050.easyfoodapi.service;

import com.im050.easyfoodapi.entity.MenuVO;
import com.im050.easyfoodcommon.entity.Shop;
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
