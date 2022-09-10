package net.hserver.hp.proxy.domian.bean;

/**
 * @author hxm
 */
public class Statistics {

    private String username;

    private Long receive;

    private Long send;

    private Long connectNum;

    private Long packNum;

    private Integer port;

    public Statistics() {
    }

    public Statistics(String username, Integer port) {
        this.username = username;
        this.receive = 0L;
        this.send = 0L;
        this.connectNum = 0L;
        this.packNum = 0L;
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getReceive() {
        return receive;
    }

    public void setReceive(Long receive) {
        this.receive = receive;
    }

    public Long getSend() {
        return send;
    }

    public void setSend(Long send) {
        this.send = send;
    }

    public Long getConnectNum() {
        return connectNum;
    }

    public void setConnectNum(Long connectNum) {
        this.connectNum = connectNum;
    }

    public Long getPackNum() {
        return packNum;
    }

    public void setPackNum(Long packNum) {
        this.packNum = packNum;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "username='" + username + '\'' +
                ", receive=" + receive +
                ", send=" + send +
                ", connectNum=" + connectNum +
                ", packNum=" + packNum +
                ", port=" + port +
                '}';
    }
}
