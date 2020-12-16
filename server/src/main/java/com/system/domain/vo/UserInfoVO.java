package com.system.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * @author hxm
 */
@Data
public class UserInfoVO {

    private List<String> roles;

    private List<Integer> rolesId;

    private String name;

    private Integer userId;

    private String username;

    private String avatar;

    private String introduction;

}
