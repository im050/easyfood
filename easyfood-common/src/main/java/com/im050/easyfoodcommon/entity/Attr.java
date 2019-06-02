package com.im050.easyfoodcommon.entity;

import java.io.Serializable;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.im050.easyfoodcommon.entity.SuperEntity;
import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author linyulin
 * @since 2019-06-01
 */
@Data
@Accessors(chain = true)
public class Attr extends SuperEntity<Attr> {
	private String name;
	private Integer shopId;
	private Integer parentId;
	private BigDecimal markup;
    /**
     * 是否可多选
     */
	private Boolean multiple;
    /**
     * 是否必选
     */
	private Boolean required;
}
