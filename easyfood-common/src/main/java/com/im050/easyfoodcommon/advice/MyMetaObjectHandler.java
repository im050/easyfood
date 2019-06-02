package com.im050.easyfoodcommon.advice;


import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Date;

public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createdAt = getFieldValByName("createdAt", metaObject);
        Object updatedAt = getFieldValByName("updatedAt", metaObject);
        if (createdAt == null) {
            setFieldValByName("createdAt", new Date(), metaObject);
        }
        if (updatedAt == null) {
            setFieldValByName("updatedAt", new Date(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("updatedAt", new Date(), metaObject);
    }
}
