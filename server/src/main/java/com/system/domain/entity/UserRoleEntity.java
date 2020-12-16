package com.system.domain.entity;

import lombok.Data;
import org.beetl.sql.annotation.entity.Table;

import java.util.Date;

/**
 * @author hxm
 */
@Data
@Table(name = "sys_user_role")
public class UserRoleEntity {
    private Integer id;
    private Integer userId;
    private Integer roleId;
    private Date createTime;
}
