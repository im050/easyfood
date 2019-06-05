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
 * 商户与商店关系表
 * </p>
 *
 * @author linyulin
 * @since 2019-06-04
 */
@Data
@Accessors(chain = true)
public class MerchantShop extends SuperEntity<MerchantShop> {


	private Integer merchantId;
	private Integer shopId;


}
