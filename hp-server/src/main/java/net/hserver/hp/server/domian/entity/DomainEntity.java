package net.hserver.hp.server.domian.entity;

import org.beetl.sql.annotation.entity.AssignID;
import org.beetl.sql.annotation.entity.Table;

/**
 * @author hxm
 */
@Table(name = "sys_domain")
public class DomainEntity {

    @AssignID
    private String id;
    private String userId;
    private String domain;
    private String customDomain;
    private String createTime;

    public String getCustomDomain() {
        if (customDomain==null){
            return "";
        }
        return customDomain;
    }

    public void setCustomDomain(String customDomain) {
        this.customDomain = customDomain;
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
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "PortEntity{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", domain=" + domain +
                ", createTime=" + createTime +
                '}';
    }
}
