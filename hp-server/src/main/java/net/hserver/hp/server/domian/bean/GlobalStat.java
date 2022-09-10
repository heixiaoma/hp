package net.hserver.hp.server.domian.bean;

import net.hserver.hp.server.domian.entity.ProxyServerEntity;
import net.hserver.hp.server.utils.DateUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

public class GlobalStat {

    public final static Map<String, List<ProxyServerEntity>> STATS = new ConcurrentHashMap<>();


    public static void add(ProxyServerEntity proxyServerEntity) {
        String name = proxyServerEntity.getName();
        List<ProxyServerEntity> stats = STATS.computeIfAbsent(name, k -> new ArrayList<>());
        stats.add(proxyServerEntity);
        while (stats.size()>500){
            stats.remove(0);
        }
    }

    public static class Stat {
        private String date;

        private Long receive;

        private Long send;

        private Long connectNum;

        private Long packNum;

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

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

}
