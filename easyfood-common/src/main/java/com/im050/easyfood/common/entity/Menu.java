package com.im050.easyfood.common.entity;


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
