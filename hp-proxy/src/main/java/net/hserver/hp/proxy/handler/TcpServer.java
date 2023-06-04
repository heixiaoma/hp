package net.hserver.hp.proxy.handler;

import cn.hserver.core.server.context.ConstConfig;
import cn.hserver.core.server.util.NamedThreadFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.hserver.hp.proxy.domian.bean.GlobalStat;
import net.hserver.hp.proxy.domian.bean.Statistics;

import java.util.concurrent.atomic.AtomicLong;


/**
 * @author hxm
 */
public class TcpServer {

    private Statistics statistics;

    private Channel tcpChannel;
    private Channel udpChannel;

    /**
     * 统计
     */

    private final AtomicLong connectNum = new AtomicLong();

    private final AtomicLong packNum = new AtomicLong();

    private final AtomicLong send = new AtomicLong();

    private final AtomicLong receive = new AtomicLong();

    private final static EventLoopGroup bossGroup = new NioEventLoopGroup(new NamedThreadFactory("boss-TcpServer"));
    private final static EventLoopGroup workerGroup = new NioEventLoopGroup(50, new NamedThreadFactory("worker-TcpServer"));

    public synchronized void bindTcp(int port, ChannelInitializer<?> channelInitializer, String username) throws InterruptedException {
        if (username != null) {
            statistics = new Statistics();
            statistics.setPort(port);
            statistics.setUsername(username);
        }
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(channelInitializer)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            b.option(ChannelOption.SO_BACKLOG, ConstConfig.backLog);
            tcpChannel = b.bind(port).sync().channel();
        } catch (Exception e) {
            throw e;
        }
    }


    public synchronized void bindUdp(int port, ChannelInitializer<?> channelInitializer, String username) throws InterruptedException {
        if (username != null) {
            statistics = new Statistics();
            statistics.setPort(port);
            statistics.setUsername(username);
        }
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .handler(channelInitializer);
            udpChannel = b.bind(port).sync().channel();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 添加链接数
     */
    public void addConnectNum() {
        GlobalStat.addConnectNum();
        connectNum.incrementAndGet();
    }

    /**
     * 添加发包数
     */
    public void addPackNum() {
        GlobalStat.addPackNum();
        packNum.incrementAndGet();
    }

    public void addSend(Long num) {
        GlobalStat.addSend(num);
        send.addAndGet(num);
    }

    public void addReceive(Long num) {
        GlobalStat.addReceive(num);
        receive.addAndGet(num);
    }

    /**
     * 获取统计
     *
     * @return
     */
    public synchronized Statistics getStatistics() {
        if (statistics == null) {
            return null;
        }
        statistics.setReceive(receive.get());
        statistics.setSend(send.get());
        statistics.setConnectNum(connectNum.get());
        statistics.setPackNum(packNum.get());
        return statistics;
    }

    public synchronized void close() {
        if (udpChannel != null) {
            udpChannel.close();
        }
        if (tcpChannel != null) {
            tcpChannel.close();
        }
    }
}
