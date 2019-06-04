package com.im050.easyfood.common.entity;

import java.math.BigDecimal;


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
