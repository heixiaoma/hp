package com.system.domain.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author hxm
 */
@Data
public class UserVO {

    private Integer id;
    private String username;
    private String nickName;
    private String avatar;
    private String password;
    private String state;
    private Date createTime;
    private Date updateTime;
    private List<UserRoleVO> roles;


    @Data
   public static class UserRoleVO {
        private Integer id;
        private String name;
        private String desc;
    }

}
