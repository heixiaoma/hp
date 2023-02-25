package net.hserver.hp.proxy.domian.bean;


import io.netty.channel.Channel;
import net.hserver.hp.proxy.utils.DateUtil;

import java.util.Date;

public class ConnectInfo {

    private String username;

    private String domain;

    private String customDomain;

    private Channel channel;

    private Integer port;

    private String ip;

    private String date;

    public String getCustomDomain() {
        return customDomain;
    }

    public void setCustomDomain(String customDomain) {
        this.customDomain = customDomain;
    }

    public ConnectInfo(Integer port,String username, String domain, Channel channel) {
        this.port=port;
        this.username = username;
        this.domain = domain;
        this.channel = channel;
        this.ip = channel.remoteAddress().toString();
        this.date = DateUtil.dateToStamp(new Date());
    }

    public ConnectInfo(Integer port,String username, String domain, String customDomain, Channel channel) {
        this.port=port;
        this.username = username;
        this.domain = domain;
        this.customDomain = customDomain;
        this.channel = channel;
        this.ip = channel.remoteAddress().toString();
        this.date = DateUtil.dateToStamp(new Date());
    }


    public ConnectInfo() {
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ConnectInfo{" +
                "username='" + username + '\'' +
                ", domain='" + domain + '\'' +
                ", customDomain='" + customDomain + '\'' +
                ", channel=" + channel +
                ", port=" + port +
                ", ip='" + ip + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
