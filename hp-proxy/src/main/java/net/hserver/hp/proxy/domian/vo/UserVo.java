package net.hserver.hp.proxy.domian.vo;

import java.util.List;
import java.util.Map;

/**
 * @author hxm
 */
public class UserVo {
    private String id;

    private String username;

    private String password;

    //-1 封号
    private Integer type;

    /**
     * 自定义的域名
     */
    private String customDomain;

    /**
     * 开通的端口号
     */
    private List<Integer> ports;

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
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCustomDomain() {
        return customDomain;
    }

    public void setCustomDomain(String customDomain) {
        this.customDomain = customDomain;
    }

    public List<Integer> getPorts() {
        return ports;
    }

    public void setPorts(List<Integer> ports) {
        this.ports = ports;
    }
}
