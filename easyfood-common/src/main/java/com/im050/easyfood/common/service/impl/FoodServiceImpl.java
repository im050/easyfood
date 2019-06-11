package com.im050.easyfood.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.im050.easyfood.common.constant.ColumnConstants;
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
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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
        if (CollectionUtils.isEmpty(attrRelationships)) {
            return ;
        }
        if (!attrRelationshipService.saveOrUpdateBatch(attrRelationships)) {
            throw new HttpException("save attr relationship failed.");
        }
    }


    @Override
    @Transactional
    public void editFoodWithAttrs(Food food) {
        // 先保存food
        food.updateById();
        // 获取原本的属性
        List<Attr> originalAttrs = attrRelationshipService.getAttrTree(food.getId());
        addAttrs(food);
        updateOrDeleteAttrs(food, originalAttrs);
    }

    private void addAttrs(Food food) {
        List<AttrRelationship> attrRelationships = new ArrayList<>();
        for (Attr attr : food.getAttrs()) {
            boolean isNew = false;
            if (attr.getId() == null || attr.getId() <= 0) {
                isNew = true;
                attr.insert();
            }
            for (Attr subAttr : attr.getAttrs()) {
                subAttr.setShopId(food.getShopId()).setParentId(attr.getId());
            }
            //批量保存属性
            if (!attrService.saveOrUpdateBatch(attr.getAttrs())) {
                throw new HttpException("save attrs failed.");
            }
            if (isNew) {
                AttrRelationship attrRelationship = new AttrRelationship();
                attrRelationship.setFoodId(food.getId())
                        .setAttrId(attr.getId());
                attrRelationships.add(attrRelationship);
            }
        }
        if (CollectionUtils.isEmpty(attrRelationships)) {
            return ;
        }
        if (!attrRelationshipService.saveOrUpdateBatch(attrRelationships)) {
            throw new HttpException("save attr relationship failed.");
        }
    }

    private void updateOrDeleteAttrs(Food food, List<Attr> originalAttrs) {
        if (CollectionUtils.isEmpty(originalAttrs)) {
            return ;
        }
        List<Attr> originalLinearAttrs = expendAttrs(originalAttrs);
        List<Attr> nowLinearAttrs = expendAttrs(food.getAttrs());
        nowLinearAttrs.removeIf(attr -> attr.getId() == null || attr.getId() < 0);
        Map<Integer, Attr> nowAttrsMap = nowLinearAttrs.stream().collect((Collectors.toMap(Attr::getId, Function.identity(), (key1, key2) -> key2)));
        List<Integer> beDeletedAttrs = new ArrayList<>();
        List<Integer> beDeletedAttrRelationships = new ArrayList<>();
        for (Attr attr : originalLinearAttrs) {
            // 如果新的map里，不存在旧的id，说明需要删除
            Attr newAttr = nowAttrsMap.get(attr.getId());
            if (newAttr == null) {
                // 如果是父级，需要删除relationships
                if (attr.getParentId() == 0) {
                    beDeletedAttrRelationships.add(attr.getId());
                }
                beDeletedAttrs.add(attr.getId());
            } else {
                UpdateWrapper<Attr> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set(ColumnConstants.NAME, newAttr.getName());
                updateWrapper.set(ColumnConstants.MARKUP, newAttr.getMarkup());
                updateWrapper.set(ColumnConstants.REQUIRED, newAttr.getRequired());
                updateWrapper.set(ColumnConstants.MULTIPLE, newAttr.getMultiple());
                updateWrapper.eq(ColumnConstants.ID, newAttr.getId());
                attrService.update(updateWrapper);
            }
        }
        //删除不用的attr和relationship
        if (beDeletedAttrs.size() > 0) {
            attrService.removeByIds(beDeletedAttrs);
        }
        if (beDeletedAttrRelationships.size() > 0) {
            QueryWrapper<AttrRelationship> queryWrapper = new QueryWrapper<>();
            queryWrapper.in(ColumnConstants.ATTR_ID, beDeletedAttrRelationships);
            queryWrapper.eq(ColumnConstants.FOOD_ID, food.getId());
            attrRelationshipService.remove(queryWrapper);
        }
    }

    /**
     * 将树形结构的Attrs展开为线性结构
     *
     * @param originalAttrs
     * @return
     */
    private List<Attr> expendAttrs(List<Attr> originalAttrs) {
        List<Attr> attrs = new ArrayList<>();
        for (Attr attr : originalAttrs) {
            if (attr.getAttrs() != null) {
                attrs.addAll(attr.getAttrs());
            }
            attr.setAttrs(null);
            attrs.add(attr);
        }
        return attrs;
    }
}
