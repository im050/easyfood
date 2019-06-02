package com.im050.easyfoodapi.advice;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import java.time.LocalDateTime;


public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createdAt = getFieldValByName("createdAt", metaObject);
        Object updatedAt = getFieldValByName("updatedAt", metaObject);
        if (createdAt == null) {
            setFieldValByName("createdAt", LocalDateTime.now(), metaObject);
        }
        if (updatedAt == null) {
            setFieldValByName("updatedAt", LocalDateTime.now(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("updatedAt", LocalDateTime.now(), metaObject);
    }
}
