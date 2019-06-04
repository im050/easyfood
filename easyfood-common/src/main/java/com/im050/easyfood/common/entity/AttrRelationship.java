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
public class AttrRelationship extends SuperEntity<AttrRelationship> {

	private Integer foodId;
	private Integer attrId;

}
