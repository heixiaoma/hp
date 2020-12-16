package com.system.domain.entity;

import lombok.Data;
import org.beetl.sql.annotation.entity.Table;

/**
 * @author hxm
 */
@Data
@Table(name = "sys_role_permission")
public class RolePermissionEntity {

    private Integer id;

    /**
     * type=0 菜单 类型
     * type=1 module 类型
     */
    private Integer type;

    private Integer roleId;

    private Integer mId;

    private String permission;

}
