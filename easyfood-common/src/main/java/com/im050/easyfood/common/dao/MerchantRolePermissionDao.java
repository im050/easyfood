package com.im050.easyfood.common.dao;

import com.im050.easyfood.common.entity.MerchantPermission;
import com.im050.easyfood.common.entity.MerchantRolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
  * 权限关联角色表 Mapper 接口
 * </p>
 *
 * @author linyulin
 * @since 2019-06-04
 */
public interface MerchantRolePermissionDao extends BaseMapper<MerchantRolePermission> {
    @Select("SELECT * FROM merchant_permission WHERE id in (\n" +
            "\tSELECT permission_id FROM merchant_role_permission WHERE role_id = (\n" +
            "\t\tSELECT `role_id` FROM merchant WHERE id = #{roleId}\n" +
            "\t)\n" +
            ")")
    List<MerchantPermission> getPermissionByRole(@Param("roleId") Integer roleId);
}