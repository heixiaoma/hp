package com.system.domain.vo;

import lombok.Data;

/**
 * @author hxm
 */
@Data
public class Meta {
    private String title;
    private String icon;

    public Meta(String title, String icon) {
        this.title = title;
        this.icon = icon;
    }

    public Meta() {
    }
}
