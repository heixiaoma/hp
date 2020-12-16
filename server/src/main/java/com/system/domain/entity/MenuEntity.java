package com.system.domain.entity;

import lombok.Data;
import org.beetl.sql.annotation.entity.Table;

/**
 * @author hxm
 */
@Data
@Table(name = "sys_menu")
public class MenuEntity {

    private Integer id;
    private String path;
    private String component;
    private String redirect;
    private String name;
    private String title;
    private String icon;
    private Integer parentId;
    private Integer sort;


}
