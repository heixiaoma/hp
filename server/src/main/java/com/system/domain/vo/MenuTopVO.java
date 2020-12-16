package com.system.domain.vo;
import lombok.Data;

import java.util.List;

/**
 * @author hxm
 */
@Data
public class MenuTopVO {
    private Integer value;
    private String label;
    private List<MenuTopVO> children;
}
