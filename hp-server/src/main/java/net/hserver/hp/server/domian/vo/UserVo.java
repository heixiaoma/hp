package net.hserver.hp.server.domian.vo;

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
     * 关闭检查
     */
    private String hasCloseCheckPhoto;

    public String getHasCloseCheckPhoto() {
        return hasCloseCheckPhoto;
    }

    public void setHasCloseCheckPhoto(String hasCloseCheckPhoto) {
        this.hasCloseCheckPhoto = hasCloseCheckPhoto;
    }

    private Integer level;

    private Map<String,String> domains;

    private List<Integer> ports;

    private String createTime;

    private String loginTime;

    private String loginIp;

    private String ipSource;

    private String tips;

    public String getIpSource() {
        if (ipSource==null){
            return "";
        }
        return ipSource;
    }

    public void setIpSource(String ipSource) {
        this.ipSource = ipSource;
    }

    public Integer getLevel() {
        return level;
    }

    public Map<String, String> getDomains() {
        return domains;
    }

    public void setDomains(Map<String, String> domains) {
        this.domains = domains;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
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

    public List<Integer> getPorts() {
        return ports;
    }

    public void setPorts(List<Integer> ports) {
        this.ports = ports;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", ports=" + ports +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
