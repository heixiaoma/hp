package net.hserver.hp.proxy.handler;

import cn.hserver.core.server.util.NamedThreadFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.hserver.hp.proxy.domian.bean.GlobalStat;
import net.hserver.hp.proxy.domian.bean.Statistics;

import java.util.concurrent.atomic.AtomicLong;


/**
 * @author hxm
 */
public class TcpServer {

    private Statistics statistics;

    private Channel channel;

    /**
     * 统计
     */

    private final AtomicLong connectNum = new AtomicLong();

    private final AtomicLong packNum = new AtomicLong();

    private final AtomicLong send = new AtomicLong();

    private final AtomicLong receive = new AtomicLong();


    public synchronized void bind(int port, ChannelInitializer channelInitializer, String username) throws InterruptedException {
        if (username!=null) {
            statistics = new Statistics();
            statistics.setPort(port);
            statistics.setUsername(username);
        }
        EventLoopGroup bossGroup = new NioEventLoopGroup(2,new NamedThreadFactory("boss-"+username));
        EventLoopGroup workerGroup = new NioEventLoopGroup(2,new NamedThreadFactory("worker-"+username));
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

    public void addSend(Long num){
        GlobalStat.addSend(num);
        send.addAndGet(num);
    }

    public void addReceive(Long num){
        GlobalStat.addReceive(num);
        receive.addAndGet(num);
    }

    /**
     * 获取统计
     *
     * @return
     */
    public synchronized Statistics getStatistics() {
        statistics.setReceive(receive.get());
        statistics.setSend(send.get());
        statistics.setConnectNum(connectNum.get());
        statistics.setPackNum(packNum.get());
        return statistics;
    }

    public synchronized void close() {
        if (channel != null) {
            channel.close();
        }
    }
}
