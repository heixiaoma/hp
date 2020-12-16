package net.hserver.hp.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.hserver.hp.initializer.ProxyServerInitializer;

/**
 * @author hxm
 */
public class ProxyServer extends Thread {

    public static final EventLoopGroup serverBossGroup = new NioEventLoopGroup();

    public static final EventLoopGroup serverWorkerGroup = new NioEventLoopGroup();

    @Override
    public void run() {
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(serverBossGroup, serverWorkerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler( new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) {
                    ch.pipeline().addLast(new FrontendHandler());
                }
            });

            b.bind("127.0.0.1", 9999)
                    .sync().channel().closeFuture().sync();
        } catch (Exception e) {
        } finally {
            serverBossGroup.shutdownGracefully();
            serverWorkerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new ProxyServer().start();
    }

}