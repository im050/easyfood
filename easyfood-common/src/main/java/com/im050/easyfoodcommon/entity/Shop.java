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
public class Shop extends SuperEntity<Shop> {

	private String name;
	private String description;
	private Integer merchantId;
	private String notice;
	private BigDecimal longitude;
	private BigDecimal latitude;
	private String address;
    /**
     * 是否被封停
     */
	private Boolean banned;
    /**
     * LOGO图片路径
     */
	private String logo;
    /**
     * 头图图片路径
     */
	private String headerBg;

}
