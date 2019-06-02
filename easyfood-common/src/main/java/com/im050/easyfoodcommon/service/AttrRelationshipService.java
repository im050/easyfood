package com.im050.easyfoodcommon.service;

import com.im050.easyfoodcommon.entity.AttrRelationship;
import com.baomidou.mybatisplus.extension.service.IService;
import com.im050.easyfoodcommon.entity.view.FoodVO;

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
    void injectAttr(List<? extends FoodVO> foods);
}
