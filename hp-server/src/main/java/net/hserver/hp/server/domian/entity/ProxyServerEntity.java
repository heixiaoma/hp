package net.hserver.hp.server.domian.entity;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.hserver.hp.server.domian.bean.ConnectInfo;
import net.hserver.hp.server.domian.bean.GlobalStat;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

public class ProxyServerEntity {

    private static final Cache<String, ProxyServerEntity> PROXY_SERVER_ENTITIES = CacheBuilder.newBuilder().expireAfterAccess(20, TimeUnit.SECONDS).build();

    public static void add(ProxyServerEntity proxyServerEntity) {
        PROXY_SERVER_ENTITIES.put(proxyServerEntity.name, proxyServerEntity);
    }

    public static Collection<ProxyServerEntity> getAll() {
        return PROXY_SERVER_ENTITIES.asMap().values();
    }

    private String name;
    private String ip;
    private String port;
    private int num;
    private ConnectInfo connectInfo;
    private GlobalStat.Stat stat;

    public GlobalStat.Stat getStat() {
        return stat;
    }

    public void setStat(GlobalStat.Stat stat) {
        this.stat = stat;
    }

    public ConnectInfo getConnectInfo() {
        return connectInfo;
    }

    public void setConnectInfo(ConnectInfo connectInfo) {
        this.connectInfo = connectInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
