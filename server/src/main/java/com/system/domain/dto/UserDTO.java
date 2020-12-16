package com.system.domain.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author hxm
 */
@Data
public class UserDTO {

    private Integer id;
    private String username;
    private String nickName;
    private String avatar;
    private String state;
    private String password;
    private Date createTime;
    private Date updateTime;
    private List<Integer> roleIds;


}
