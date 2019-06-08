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

    /**
     * 获取商户的门店列表
     *
     * @param merchantId
     * @return
     */
    List<Shop> getMerchantShop(Integer merchantId);

    /**
     * 校验商店是否归属某商户
     *
     * @param shopId
     * @param merchantId
     * @return
     */
    boolean checkShopOwner(Integer shopId, Integer merchantId);

    /**
     * 获取商店的所有菜单和餐品
     *
     * @param shopId
     * @return
     */
    List<MenuVO> getAllFoodsWithMenu(Integer shopId);
}
