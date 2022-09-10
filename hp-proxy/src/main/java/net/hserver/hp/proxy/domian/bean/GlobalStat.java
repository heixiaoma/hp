package net.hserver.hp.proxy.domian.bean;

import net.hserver.hp.proxy.utils.DateUtil;

import java.util.*;
import java.util.concurrent.atomic.LongAdder;

public class GlobalStat {

    public final static List<Stat> STATS = new ArrayList<>();

    private static final LongAdder connectNum = new LongAdder();

    private static final LongAdder packNum = new LongAdder();

    private static final LongAdder send = new LongAdder();

    private static final LongAdder receive = new LongAdder();


    /**
     * 添加连接数
     */
    public static void addConnectNum() {
        connectNum.increment();
    }

    /**
     * 添加发包总量
     */
    public static void addPackNum() {
        packNum.increment();
    }

    /**
     * 发送字节大小
     *
     */
    public static void addSend(long sendSize) {
        send.add(sendSize);
    }

    /**
     * 接受字节大小
     */
    public static void addReceive(long receiveSize) {
        receive.add(receiveSize);
    }


    public static void clear() {
        Stat stat = new Stat();
        stat.setReceive(receive.longValue());
        receive.reset();
        stat.setConnectNum(connectNum.longValue());
        connectNum.reset();
        stat.setSend(send.longValue());
        send.reset();
        stat.setPackNum(packNum.longValue());
        packNum.reset();
        stat.setDate(DateUtil.dateToStamp(new Date()));
        STATS.add(stat);
        while (STATS.size()>500){
            STATS.remove(0);
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
