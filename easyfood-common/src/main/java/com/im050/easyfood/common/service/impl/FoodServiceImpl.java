package com.im050.easyfood.common.service.impl;

import com.im050.easyfood.common.entity.Attr;
import com.im050.easyfood.common.entity.AttrRelationship;
import com.im050.easyfood.common.entity.Food;
import com.im050.easyfood.common.dao.FoodDao;
import com.im050.easyfood.common.entity.view.AttrVO;
import com.im050.easyfood.common.exception.HttpException;
import com.im050.easyfood.common.service.AttrRelationshipService;
import com.im050.easyfood.common.service.AttrService;
import com.im050.easyfood.common.service.FoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author linyulin
 * @since 2019-06-01
 */
@Service
public class FoodServiceImpl extends ServiceImpl<FoodDao, Food> implements FoodService {

    @Autowired
    private AttrRelationshipService attrRelationshipService;

    @Autowired
    private AttrService attrService;

    @Transactional
    public void addFoodWithAttrs(Food food) {
        food.insert();
        Integer foodId = food.getId();

        List<Attr> attrs = food.getAttrs();
        List<AttrRelationship> attrRelationships = new ArrayList<>();
        //TODO: 这里可以加强验证attrs的属性，譬如名称是否为空
        for (Attr attr : attrs) {
            attr.setShopId(food.getShopId()); //防止用户传进来的shopId是篡改过的
            attr.insert();
            Integer parentId = attr.getId();
            for (Attr subAttr : attr.getAttrs()) {
                subAttr.setShopId(food.getShopId()).setParentId(parentId);
            }
            //批量保存属性
            if (!attrService.saveOrUpdateBatch(attr.getAttrs())) {
                throw new HttpException("save attrs failed.");
            }
            AttrRelationship attrRelationship = new AttrRelationship();
            attrRelationship.setAttrId(parentId)
                    .setFoodId(foodId);
            attrRelationships.add(attrRelationship);
        }
        //批量保存关系
        if (!attrRelationshipService.saveOrUpdateBatch(attrRelationships)) {
            throw new HttpException("save attr relationship failed.");
        }
    }
}
