package com.im050.easyfood.common.dao;

import com.im050.easyfood.common.entity.Shop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author linyulin
 * @since 2019-06-01
 */
public interface ShopDao extends BaseMapper<Shop> {
    @Select("SELECT * FROM shop WHERE id IN (SELECT shop_id FROM merchant_shop WHERE merchant_id = #{merchantId})")
    List<Shop> selectMerchantShop(@Param("merchantId") Integer merchantId);
}