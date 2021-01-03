package net.hserver.hp.server.domian.entity;

import org.beetl.sql.annotation.entity.AssignID;
import org.beetl.sql.annotation.entity.Table;

import java.util.Date;

/**
 * @author hxm
 */
@Table(name = "sys_port")
public class PortEntity {

    @AssignID
    private String id;
    private String userId;
    private Integer port;
    private String createTime;

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

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
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
                ", port=" + port +
                ", createTime=" + createTime +
                '}';
    }
}
