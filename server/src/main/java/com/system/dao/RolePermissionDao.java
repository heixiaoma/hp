package com.system.dao;

import com.system.domain.entity.RolePermissionEntity;
import net.hserver.plugins.beetlsql.annotation.BeetlSQL;
import org.beetl.sql.mapper.BaseMapper;

/**
 * @author hxm
 */
@BeetlSQL
public interface RolePermissionDao extends BaseMapper<RolePermissionEntity> {


    default void deleteMenuPermission(Integer roleId) {
        executeUpdate("delete FROM  sys_role_permission where type = 0 and role_id = ?", roleId);
    }

    default void updateModulePermission(Integer roleId) {
        executeUpdate("delete FROM  sys_role_permission where type = 1 and role_id = ?", roleId);
    }

}
