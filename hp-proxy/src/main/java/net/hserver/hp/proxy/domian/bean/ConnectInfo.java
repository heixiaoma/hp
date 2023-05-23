package net.hserver.hp.proxy.domian.bean;


import io.netty.channel.Channel;
import lombok.Data;
import net.hserver.hp.proxy.utils.DateUtil;

import java.util.Date;

@Data
public class ConnectInfo {

    /**
     * 用户名
     */
    private String username;

    /**
     * 自定义域名前缀
     */
    private String domain;

    /**
     * 用户自定义域名 **.com
     */
    private String customDomain;

    /**
     * 链接通道
     */
    private Channel channel;

    /**
     * 来源端口
     */
    private Integer port;

    /**
     * 来源IP
     */
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

}
