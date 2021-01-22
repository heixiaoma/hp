package net.hserver.hp.server.domian.entity;

import org.beetl.sql.annotation.entity.AssignID;
import org.beetl.sql.annotation.entity.Table;

/**
 * @author hxm
 */
@Table(name = "sys_app")
public class AppEntity {

    @AssignID
    private String id;
    private String versionCode;
    private String updateContent;
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "AppEntity{" +
                "id='" + id + '\'' +
                ", versionCode='" + versionCode + '\'' +
                ", updateContent='" + updateContent + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
