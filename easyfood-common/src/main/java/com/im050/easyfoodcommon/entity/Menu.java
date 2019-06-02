package com.im050.easyfoodcommon.entity;

import java.io.Serializable;

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
public class Menu extends SuperEntity<Menu> {

	private Integer shopId;
	private String name;
	private String description;
	private Boolean enabled;
	private Integer sort;
}
