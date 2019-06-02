package com.im050.easyfoodapi.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.im050.easyfoodcommon.constant.ColumnConstants;
import com.im050.easyfoodcommon.utils.sqlhelper.Fields;

import java.util.HashMap;

public class SuperServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    /**
     * 根据字段、条件增加减少积分
     *
     * @param fields
     * @param updateWrapper
     * @return
     */
    public Boolean incr(Fields fields, UpdateWrapper updateWrapper) {
        for (HashMap<String, Object> item : fields
        ) {
            updateWrapper.setSql(item.get("field") + "=" + item.get("field") + "+" + item.get("value"));
        }
        return super.update(updateWrapper);
    }

    /**
     * 根据ID主键增加字段值
     *
     * @param id
     * @param score
     * @param column
     * @return
     */
    public Boolean incr(Integer id, Number score, String column) {
        return incr(ColumnConstants.ID, id, score, column);
    }

    /**
     * 根据任意主键增加字段值
     *
     * @param pkName
     * @param pkValue
     * @param score
     * @param column
     * @return
     */
    public Boolean incr(String pkName, Object pkValue, Number score, String column) {
        Fields fields = new Fields();
        fields.addField(column, score);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq(pkName, pkValue);
        return incr(fields, updateWrapper);
    }

    public Boolean decr(Fields fields, UpdateWrapper updateWrapper) {
        for (HashMap<String, Object> item : fields
        ) {
            updateWrapper.setSql(item.get("field") + "=" + item.get("field") + "-" + item.get("value"));
        }
        return super.update(updateWrapper);
    }

    public Boolean decr(String pkName, Object pkValue, Number score, String column, Boolean geScore) {
        Fields fields = new Fields();
        fields.addField(column, score);
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq(pkName, pkValue);
        if (geScore) {
            updateWrapper.ge(column, score);
        }
        return decr(fields, updateWrapper);
    }

    public Boolean decr(String pkName, Object pkValue, Number score, String column) {
        return decr(pkName, pkValue, score, column, true);
    }

    public Boolean decr(Object pkValue, Number score, String column) {
        return decr(ColumnConstants.ID, pkValue, score, column, true);
    }

}
