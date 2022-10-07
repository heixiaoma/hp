package net.hserver.hp.proxy.config;

import cn.hserver.core.ioc.annotation.Bean;
import cn.hserver.core.ioc.annotation.Value;

@Bean
public class WebConfig {

    @Value("adminAddress")
    private String adminAddress;

    @Value("host")
    private String host;

    @Value("userHost")
    private String userHost;

    @Value("notReg")
    private Boolean notReg;

    @Value("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdminAddress() {
        return adminAddress;
    }

    public void setAdminAddress(String adminAddress) {
        this.adminAddress = adminAddress;
    }

    public String getHost() {
        return host;
    }

    public Boolean getNotReg() {
        return notReg;
    }

    public String getUserHost() {
        return userHost;
    }

    public void setUserHost(String userHost) {
        this.userHost = userHost;
    }

    public void setNotReg(Boolean notReg) {
        this.notReg = notReg;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String toString() {
        return "WebConfig{" +
                "adminAddress='" + adminAddress + '\'' +
                ", host='" + host + '\'' +
                '}';
    }
}
