package com.im050.easyfoodapi.service;

import com.im050.easyfoodapi.entity.AttrVO;
import com.im050.easyfoodapi.entity.FoodVO;
import com.im050.easyfoodcommon.entity.AttrRelationship;
import com.baomidou.mybatisplus.extension.service.IService;
import com.im050.easyfoodcommon.entity.Food;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author linyulin
 * @since 2019-06-01
 */
public interface AttrRelationshipService extends IService<AttrRelationship> {
    void injectAttr(List<? extends FoodVO> foods);
}
