package net.hserver.hp.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.hserver.hp.initializer.BaseServerInitializer;

/**
 * @author hxm
 */
public class BaseServer extends Thread {

    public static final EventLoopGroup serverBossGroup = new NioEventLoopGroup();

    public static final EventLoopGroup serverWorkerGroup = new NioEventLoopGroup();

    @Override
    public void run() {
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(serverBossGroup, serverWorkerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new BaseServerInitializer());
            b.bind("127.0.0.1", 10000).sync().channel().closeFuture().sync();
        } catch (Exception e) {
        } finally {
            serverBossGroup.shutdownGracefully();
            serverWorkerGroup.shutdownGracefully();
        }
    }
}