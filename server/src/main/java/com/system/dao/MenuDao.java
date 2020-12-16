package com.system.dao;

import com.system.domain.entity.MenuEntity;
import net.hserver.plugins.beetlsql.annotation.BeetlSQL;
import org.beetl.sql.mapper.BaseMapper;

import java.util.List;

/**
 * @author hxm
 */
@BeetlSQL
public interface MenuDao extends BaseMapper<MenuEntity> {

    /**
     * 按角色ID查找菜单
     *
     * @param roleIds
     * @return
     */
    default List<MenuEntity> findMenuByRoleIds(Integer[] roleIds) {
        StringBuilder sql = new StringBuilder();
        sql.append(" and (");
        for (int i = 0; i < roleIds.length; i++) {
            if (i == 0) {
                sql.append(" role_id =").append(roleIds[i]);
            } else {
                sql.append(" or role_id =").append(roleIds[i]);
            }
        }
        sql.append(" )");
        return execute("SELECT DISTINCT sys_menu.* FROM sys_role_permission,sys_menu WHERE sys_role_permission.type=0 and sys_role_permission.m_id=sys_menu.id" + sql);
    }
}
