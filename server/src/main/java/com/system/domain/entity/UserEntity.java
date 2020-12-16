package com.system.domain.entity;

import lombok.Data;
import org.beetl.sql.annotation.entity.Table;

import java.util.Date;

/**
 * @author hxm
 */
@Data

@Table(name = "sys_user")
public class UserEntity {

    private Integer id;
    private String username;
    private String password;
    private String nickName;
    private String avatar;
    private String state;
    private Date createTime;
    private Date updateTime;

}
