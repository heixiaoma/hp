package net.hserver.hp.server.domian.entity;

import org.beetl.sql.annotation.entity.AssignID;
import org.beetl.sql.annotation.entity.Table;

import java.util.Date;

/**
 * @author hxm
 */
@Table(name = "sys_user")
public class UserEntity {

    @AssignID
    private String id;
    private String username;
    private String password;
    /**
     * 1 系统用户 2 普通用户 -1 被封用户
     */
    private Integer type;
    //级别到时一些优惠或者限定服务器可以选择
    private Integer level;

    /**
     * 关闭检查
     */
    private String hasCloseCheckPhoto;

    public String getHasCloseCheckPhoto() {
        return hasCloseCheckPhoto;
    }

    public void setHasCloseCheckPhoto(String hasCloseCheckPhoto) {
        this.hasCloseCheckPhoto = hasCloseCheckPhoto;
    }

    private String createTime;
    private String loginTime;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        if (type==null){
            return 0;
        }
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                '}';
    }
}
