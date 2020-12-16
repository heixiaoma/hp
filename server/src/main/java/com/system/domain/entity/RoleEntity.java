package com.system.domain.entity;

import lombok.Data;
import org.beetl.sql.annotation.entity.Table;

/**
 * @author hxm
 */
@Data
@Table(name = "sys_role")
public class RoleEntity {
    private Integer id;
    private String name;
    private String desc;
}
