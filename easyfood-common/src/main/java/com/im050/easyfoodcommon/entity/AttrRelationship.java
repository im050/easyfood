package com.im050.easyfoodcommon.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
public class AttrRelationship extends SuperEntity<AttrRelationship> {

	private Integer foodId;
	private Integer attrId;

}
