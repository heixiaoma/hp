package net.hserver.hp.server.init;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.traffic.GlobalChannelTrafficShapingHandler;
import io.netty.handler.traffic.TrafficCounter;
import net.hserver.hp.server.domian.bean.Statistics;

import java.util.concurrent.atomic.AtomicLong;


/**
 * @author hxm
 */
public class TcpServer {

    private Statistics statistics;

    private Channel channel;

    private AtomicLong connectNum = new AtomicLong();

    private AtomicLong packNum = new AtomicLong();

    /**
     * 流量统计
     */
    private GlobalChannelTrafficShapingHandler globalChannelTrafficShapingHandler;

    public synchronized void setGlobalChannelTrafficShapingHandler(GlobalChannelTrafficShapingHandler globalChannelTrafficShapingHandler) {
        this.globalChannelTrafficShapingHandler = globalChannelTrafficShapingHandler;
    }

    public synchronized void bind(int port, ChannelInitializer channelInitializer, String username) throws InterruptedException {
        if (username!=null) {
            statistics = new Statistics();
            statistics.setPort(port);
            statistics.setUsername(username);
        }
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(channelInitializer)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            channel = b.bind(port).sync().channel();
            channel.closeFuture().addListener((ChannelFutureListener) future -> {
                workerGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();
            });
        } catch (Exception e) {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            throw e;
        }
    }

    /**
     * 添加链接数
     */
    public void addConnectNum() {
        connectNum.incrementAndGet();
    }

    /**
     * 添加发包数
     */
    public void addPackNum() {
        packNum.incrementAndGet();
    }

    /**
     * 获取统计
     *
     * @return
     */
    public synchronized Statistics getStatistics() {
        TrafficCounter trafficCounter = globalChannelTrafficShapingHandler.trafficCounter();
        statistics.setReceive(trafficCounter.cumulativeReadBytes());
        statistics.setSend(trafficCounter.cumulativeWrittenBytes());
        statistics.setConnectNum(connectNum.get());
        statistics.setPackNum(packNum.get());
        return statistics;
    }

    public synchronized void close() {
        if (channel != null) {
            globalChannelTrafficShapingHandler = null;
            channel.close();
        }
    }
}
