package net.hserver.hp.proxy.handler;

import cn.hserver.core.server.context.ConstConfig;
import cn.hserver.core.server.util.NamedThreadFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;



/**
 * @author hxm
 */
public class TcpServer {

    private Channel tcpChannel;
    private Channel udpChannel;

    private final static EventLoopGroup bossGroup = new NioEventLoopGroup(new NamedThreadFactory("boss-TcpServer"));
    private final static EventLoopGroup workerGroup = new NioEventLoopGroup(50, new NamedThreadFactory("worker-TcpServer"));

    public synchronized void bindTcp(int port, ChannelInitializer<?> channelInitializer, String username) throws InterruptedException {
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


    public synchronized void close() {
        if (udpChannel != null) {
            udpChannel.close();
        }
        if (tcpChannel != null) {
            tcpChannel.close();
        }
    }
}
