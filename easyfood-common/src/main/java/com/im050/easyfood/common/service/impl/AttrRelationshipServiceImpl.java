package com.im050.easyfood.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.im050.easyfood.common.constant.ColumnConstants;
import com.im050.easyfood.common.entity.Attr;
import com.im050.easyfood.common.entity.AttrRelationship;
import com.im050.easyfood.common.entity.Food;
import com.im050.easyfood.common.entity.view.AttrVO;
import com.im050.easyfood.common.entity.view.FoodVO;
import com.im050.easyfood.common.service.AttrService;
import com.im050.easyfood.common.utils.Tools;
import com.im050.easyfood.common.dao.AttrRelationshipDao;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.im050.easyfood.common.service.AttrRelationshipService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author linyulin
 * @since 2019-06-01
 */
@Service
public class AttrRelationshipServiceImpl extends ServiceImpl<AttrRelationshipDao, AttrRelationship> implements AttrRelationshipService {


    @Autowired
    AttrService attrService;

    @Autowired
    AttrRelationshipDao attrRelationshipDao;

    @Override
    public void injectAttr(Food food) {
        List<Food> foods = new ArrayList<>();
        foods.add(food);
        injectAttr(foods);
    }

    @Override
    public void injectAttr(List<? extends Food> foods) {
        if (CollectionUtils.isEmpty(foods)) {
            return ;
        }
        List<Integer> foodIds = foods.stream().map(Food::getId).collect(Collectors.toList());
        //根据foodIds取出所有relationship
        List<AttrRelationship> attrRelationships = getByFoodIds(foodIds);

        if (CollectionUtils.isEmpty(attrRelationships)) {
            return;
        }

        List<Integer> ids = attrRelationships.stream().map(AttrRelationship::getAttrId).collect(Collectors.toList());

        //取出所有所有Attrs
        List<Attr> attrs = attrRelationshipDao.getAttrsWithIds(ids);

        if (CollectionUtils.isEmpty(attrs)) {
            return;
        }

        // 生成树形结构的attrs
        List<Attr> attrParentTree = buildAttrParentTree(attrs);

        //根据ID创建map
        Map<Integer, Attr> parentAttrMap = attrParentTree.stream().collect(Collectors.toMap(Attr::getId, Function.identity(), (key1, key2) -> key2));
        //餐品ID和属性的对应map
        Map<Integer, List<AttrRelationship>> attrRelationshipMap = attrRelationships.stream().collect(Collectors.groupingBy(AttrRelationship::getFoodId));
        foods.forEach(food -> {
            List<AttrRelationship> foodAttrRelationships = attrRelationshipMap.get(food.getId());
            if (CollectionUtils.isEmpty(foodAttrRelationships)) {
                return;
            }
            List<Attr> foodAttrs = new ArrayList<>();
            foodAttrRelationships.forEach(attrRelationship -> {
                Integer attrId = attrRelationship.getAttrId();
                Attr attr = parentAttrMap.get(attrId);
                foodAttrs.add(attr);
            });
            food.setAttrs(foodAttrs);
        });
    }

    @Override
    public List<Attr> getAttrTree(Integer foodId) {
        List<AttrRelationship> attrRelationships = getByFoodIds(foodId);
        List<Integer> ids = attrRelationships.stream().map(AttrRelationship::getAttrId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        List<Attr> attrs = attrRelationshipDao.getAttrsWithIds(ids);
        return buildAttrParentTree(attrs);
    }

    private List<AttrRelationship> getByFoodIds(List<Integer> foodIds) {
        QueryWrapper<AttrRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.in(ColumnConstants.FOOD_ID, foodIds);
        return list(queryWrapper);
    }

    private List<AttrRelationship> getByFoodIds(Integer... foodIds) {
        return getByFoodIds(Arrays.asList(foodIds));
    }

    private List<Attr> buildAttrParentTree(List<Attr> attrs) {
        //父子关系attr
        Map<Integer, List<Attr>> attrGroup = attrs.stream().collect(Collectors.groupingBy(Attr::getParentId));
        List<Attr> parentAttr = new ArrayList<>();
        //整理父子关系
        attrs.forEach(attr -> {
            //子分类不处理
            if (attr.getParentId() > 0) {
                return;
            }
            Integer id = attr.getId();
            List<Attr> children = attrGroup.get(id);
            if (!CollectionUtils.isEmpty(children)) {
                attr.setAttrs(children);
            }
            parentAttr.add(attr);
        });
        return parentAttr;
    }
}
