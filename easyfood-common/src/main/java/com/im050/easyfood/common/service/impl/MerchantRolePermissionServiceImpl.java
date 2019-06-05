package com.im050.easyfood.common.service.impl;

import com.im050.easyfood.common.dao.MerchantRoleDao;
import com.im050.easyfood.common.entity.MerchantPermission;
import com.im050.easyfood.common.entity.MerchantRole;
import com.im050.easyfood.common.entity.MerchantRolePermission;
import com.im050.easyfood.common.dao.MerchantRolePermissionDao;
import com.im050.easyfood.common.service.MerchantPermissionService;
import com.im050.easyfood.common.service.MerchantRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.im050.easyfood.common.service.MerchantRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Permission;
import java.util.List;

/**
 * <p>
 * 权限关联角色表 服务实现类
 * </p>
 *
 * @author linyulin
 * @since 2019-06-04
 */
@Service
public class MerchantRolePermissionServiceImpl extends ServiceImpl<MerchantRolePermissionDao, MerchantRolePermission> implements MerchantRolePermissionService {

    @Autowired
    private MerchantRolePermissionDao merchantRolePermissionDao;

    @Autowired
    private MerchantPermissionService merchantPermissionService;

    @Autowired
    private MerchantRoleService merchantRoleService;

    public List<MerchantPermission> getPermissionByRole(Integer roleId) {
        MerchantRole merchantRole = merchantRoleService.getById(roleId);
        //如果分组是管理员，直接返回所有权限
        if (merchantRole.getAdmin()) {
            return merchantPermissionService.list();
        }
        return merchantRolePermissionDao.getPermissionByRole(roleId);
    }
}
