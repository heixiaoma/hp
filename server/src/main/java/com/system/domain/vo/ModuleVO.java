package com.system.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @author hxm
 */
@Data
public class ModuleVO {

    private String permission;

    private String title;

    private List<ModuleVO> children;
}
