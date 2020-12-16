package net.hserver.hp.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import net.hserver.hp.initializer.ProxyInitializer;

/**
 * @author hxm
 */
public class ProxyClient extends Thread{

    public static Channel channel;

    @Override
    public void run() {
        try {
            final EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class);
            b.handler(new ProxyInitializer());
            final ChannelFuture future = b.connect("127.0.0.1", 9999).sync();
            future.addListener((ChannelFutureListener) arg0 -> {
                if (future.isSuccess()) {
                    System.out.println("连接服务器成功");
                } else {
                    System.out.println("连接服务器失败");
                    future.cause().printStackTrace();
                    group.shutdownGracefully(); //关闭线程组
                }
            });
            channel = future.channel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
