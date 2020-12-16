package com.system.domain.vo;

import lombok.Data;

/**
 * @author hxm
 */
@Data
public class LoginVO {

    private String token;

    private String username;

    private Integer userId;

    private String nickName;

    private String avatar;

    private Integer[] roleIds;

    public LoginVO() {

    }

    public LoginVO(String token, String username, Integer userId, String nickName, String avatar, Integer[] roleIds) {
        this.token = token;
        this.username = username;
        this.userId = userId;
        this.nickName = nickName;
        this.avatar = avatar;
        this.roleIds = roleIds;
    }
}
