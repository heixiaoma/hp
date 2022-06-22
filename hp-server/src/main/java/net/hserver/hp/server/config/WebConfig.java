package net.hserver.hp.server.config;

import cn.hserver.core.ioc.annotation.Bean;
import cn.hserver.core.ioc.annotation.Value;

@Bean
public class WebConfig {

    @Value("host")
    private String host;

    @Value("password")
    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "WebConfig{" +
                "host='" + host + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
