package com.im050.easyfoodapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.im050.easyfoodapi.entity.AttrVO;
import com.im050.easyfoodapi.entity.FoodVO;
import com.im050.easyfoodapi.service.AttrService;
import com.im050.easyfoodcommon.constant.ColumnConstants;
import com.im050.easyfoodcommon.entity.Attr;
import com.im050.easyfoodcommon.entity.AttrRelationship;
import com.im050.easyfoodapi.dao.AttrRelationshipDao;
import com.im050.easyfoodapi.service.AttrRelationshipService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.im050.easyfoodcommon.entity.Food;
import com.im050.easyfoodcommon.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
    public void injectAttr(List<? extends FoodVO> foods) {
        List<Integer> foodIds = foods.stream().map(FoodVO::getId).collect(Collectors.toList());
        //根据foodIds取出所有relationship
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in(ColumnConstants.FOOD_ID, foodIds);
        List<AttrRelationship> attrRelationships = list(queryWrapper);
        List<Integer> ids = attrRelationships.stream().map(AttrRelationship::getAttrId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        List<Attr> attrs = attrRelationshipDao.getAttrsWithIds(ids);
        List<AttrVO> attrVOS = Tools.changeToVo(attrs, AttrVO.class);
        if (CollectionUtils.isEmpty(attrVOS)) {
            return;
        }
        //父子关系attr
        Map<Integer, List<AttrVO>> attrGroup = attrVOS.stream().collect(Collectors.groupingBy(AttrVO::getParentId));
        List<AttrVO> parentAttr = new ArrayList<>();
        //整理父子关系
        attrVOS.forEach(attr -> {
            //子分类不处理
            if (attr.getParentId() > 0) {
                return;
            }
            Integer id = attr.getId();
            List<AttrVO> children = attrGroup.get(id);
            if (CollectionUtils.isEmpty(children)) {
                return;
            }
            attr.setAttrs(children);
            parentAttr.add(attr);
        });
        //根据ID创建map
        Map<Integer, AttrVO> parentAttrVOMap = parentAttr.stream().collect(Collectors.toMap(AttrVO::getId, Function.identity(), (key1, key2) -> key2));
        //餐品ID和属性的对应map
        Map<Integer, List<AttrRelationship>> attrRelationshipMap = attrRelationships.stream().collect(Collectors.groupingBy(AttrRelationship::getFoodId));
        foods.forEach(food -> {
            List<AttrRelationship> foodAttrs = attrRelationshipMap.get(food.getId());
            if (CollectionUtils.isEmpty(foodAttrs)) {
                return;
            }
            List<AttrVO> foodAttrVOS = new ArrayList<>();
            foodAttrs.forEach(attrRelationship -> {
                Integer attrId = attrRelationship.getAttrId();
                AttrVO attrVO = parentAttrVOMap.get(attrId);
                foodAttrVOS.add(attrVO);
            });
            food.setAttrs(foodAttrVOS);
        });
    }
}
