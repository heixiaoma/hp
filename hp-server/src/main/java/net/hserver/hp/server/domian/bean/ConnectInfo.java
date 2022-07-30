package net.hserver.hp.server.domian.bean;


import io.netty.channel.Channel;
import net.hserver.hp.server.utils.DateUtil;

import java.util.Date;

public class ConnectInfo {

    private String username;

    private Channel channel;

    private String ip;

    private String date;

    public ConnectInfo(String username, Channel channel) {
        this.username = username;
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
