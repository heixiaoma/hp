package com.system.domain.entity;

import lombok.Data;
import org.beetl.sql.annotation.entity.AssignID;
import org.beetl.sql.annotation.entity.Table;

/**
 * @author hxm
 */
@Data
@Table(name = "sys_module")
public class ModuleEntity {

    @AssignID
    private String permission;

    private String name;

}
