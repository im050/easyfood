package com.im050.easyfood.common.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class Tools {

    public static <T> List<T> changeToVo(List<?> list, Class<T> c) {
        List<T> finalList = new ArrayList<>();
        list.forEach(x -> {
            try {
                T obj = c.newInstance();
                BeanUtils.copyProperties(x, obj);
                finalList.add(obj);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return finalList;
    }

}
