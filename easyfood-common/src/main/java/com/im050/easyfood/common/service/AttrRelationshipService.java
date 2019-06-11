package com.im050.easyfood.common.service;

import com.im050.easyfood.common.entity.AttrRelationship;
import com.im050.easyfood.common.entity.Food;
import com.im050.easyfood.common.entity.view.FoodVO;
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
public interface AttrRelationshipService extends IService<AttrRelationship> {
    void injectAttr(Food food);
    void injectAttr(List<? extends Food> foods);
}
