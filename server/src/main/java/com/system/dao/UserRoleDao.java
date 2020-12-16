package com.system.dao;

import com.system.domain.entity.UserRoleEntity;
import net.hserver.plugins.beetlsql.annotation.BeetlSQL;
import org.beetl.sql.mapper.BaseMapper;

/**
 * @author hxm
 */
@BeetlSQL
public interface UserRoleDao extends BaseMapper<UserRoleEntity> {

    default boolean deleteUserId(Integer userId) {
        return executeUpdate("delete from sys_user_role where user_id = ?", userId) > 0;
    }
}
