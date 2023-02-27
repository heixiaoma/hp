package net.hserver.hp.server.domian.bean;

import net.hserver.hp.server.domian.entity.DomainEntity;

public class DomainVo {

    private String id;
    private String userId;
    private String domain;
    private String customDomain;
    private String createTime;
    private String username;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDomain() {
        if (domain==null){
            return "";
        }
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCustomDomain() {
        if (customDomain==null){
            return "";
        }
        return customDomain;
    }

    public void setCustomDomain(String customDomain) {
        this.customDomain = customDomain;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
