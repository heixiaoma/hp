package com.system.domain.entity;

import lombok.Data;
import org.beetl.sql.annotation.entity.AssignID;
import org.beetl.sql.annotation.entity.Table;

import java.util.Date;

/**
 * @author hxm
 */
@Data
@Table(name = "sys_token")
public class TokenEntity {
    @AssignID
    private String token;
    private Integer userId;
    private Date createTime;

}
