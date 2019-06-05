package com.im050.easyfood.common.service;

import com.im050.easyfood.common.entity.MerchantPermission;
import com.im050.easyfood.common.entity.MerchantRolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限关联角色表 服务类
 * </p>
 *
 * @author linyulin
 * @since 2019-06-04
 */
public interface MerchantRolePermissionService extends IService<MerchantRolePermission> {
    List<MerchantPermission> getPermissionByRole(Integer roleId);
}
