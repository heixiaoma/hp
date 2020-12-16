package com.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @author hxm
 */
@Data
public class MenuVO {
    private Integer id;
    private String path;
    private String component;
    private String redirect;
    private String name;
    private String title;
    private String icon;
    private Meta meta;
    private Integer parentId;
    private Integer sort;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MenuVO> children;
}
