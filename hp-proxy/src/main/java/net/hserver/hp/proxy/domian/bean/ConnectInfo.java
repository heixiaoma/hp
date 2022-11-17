package net.hserver.hp.proxy.domian.bean;


import io.netty.channel.Channel;
import net.hserver.hp.proxy.utils.DateUtil;

import java.util.Date;

public class ConnectInfo {

    private String  username;

    private String domain;

    private Channel channel;

    private String ip;

    private String date;

    public ConnectInfo(String username,String domain, Channel channel) {
        this.username = username;
        this.domain = domain;
        this.channel = channel;
        this.ip = channel.remoteAddress().toString();
        this.date= DateUtil.dateToStamp(new Date());
    }

    public ConnectInfo() {
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
}
