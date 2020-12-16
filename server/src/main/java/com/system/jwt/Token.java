package com.system.jwt;

import lombok.Data;

/**
 * @author hxm
 */
@Data
public class Token {
    private String tokenStr;
    private Integer userId;
    private String username;
    private Integer[] roleIds;
    private Long exp;
}
