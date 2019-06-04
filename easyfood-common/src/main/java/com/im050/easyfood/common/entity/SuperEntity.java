package com.im050.easyfood.common.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Accessors(chain = true)
public class SuperEntity<T extends Model<?>> extends Model<T> {

    @TableId(value="id", type= IdType.AUTO)
    private Integer id;

    /**
     * 创建时间
     */
    @JsonIgnore
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private Date createdAt;

    /**
     * 更新时间
     */
    @JsonIgnore
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;

    /**
     * 删除标记(0:未删除 1:已删除)
     */
    @JsonIgnore
    private Boolean delStatus;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
