package com.im050.easyfoodcommon.entity;

import java.io.Serializable;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.im050.easyfoodcommon.entity.SuperEntity;
import java.io.Serializable;
import java.util.Date;


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
public class Food extends SuperEntity<Food> {

	private String name;
	private String description;
	private Integer menuId;
	private BigDecimal price;
	private Integer stock;
	private Integer maxStock;
	private Boolean enabled;
	private String pic;
    /**
     * 次日置满库存
     */
    @JsonIgnore
	private Boolean autoUpdateStock;
    /**
     * 上次更新库存时间
     */
    @JsonIgnore
	private Date stockUpdatedAt;

}
