package com.im050.easyfood.common.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author linyulin
 * @since 2019-06-04
 */
@Data
@Accessors(chain = true)
public class MerchantPermission extends SuperEntity<MerchantPermission> {

    /**
     * 归属菜单,前端判断并展示菜单使用,
     */
	private String menuCode;
    /**
     * 菜单的中文释义
     */
	private String menuName;
    /**
     * 权限的代码/通配符,对应代码中@RequiresPermissions 的value
     */
	private String permissionCode;
    /**
     * 本权限的中文释义
     */
	private String permissionName;
    /**
     * 是否本菜单必选权限
     */
	private Boolean required;


}
