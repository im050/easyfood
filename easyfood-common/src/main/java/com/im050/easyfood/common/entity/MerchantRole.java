package com.im050.easyfood.common.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author linyulin
 * @since 2019-06-04
 */
@Data
@Accessors(chain = true)
public class MerchantRole extends SuperEntity<MerchantRole> {

	private Integer merchantId;
	private String name;
    /**
     * 是否管理员，如果是的话，则默认拥有所有权限
     */
	private Boolean admin;

}
