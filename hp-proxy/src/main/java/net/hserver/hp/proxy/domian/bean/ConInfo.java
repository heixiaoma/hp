package net.hserver.hp.proxy.domian.bean;


import java.util.concurrent.atomic.AtomicLong;


public class ConInfo {
    private String username;
    private AtomicLong count = new AtomicLong();

    public ConInfo() {
    }

    public ConInfo(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AtomicLong getCount() {
        return count;
    }

    public void setCount(AtomicLong count) {
        this.count = count;
    }
}
