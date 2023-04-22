package net.hserver.hp.common;

import cn.hserver.core.server.ServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.hserver.hp.common.handler.PhotoJpgMessageHandler;
import net.hserver.hp.common.handler.PhotoPngMessageHandler;

public class Test {

    public static void main(String[] args) throws Exception {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup()).channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                channel.pipeline().addLast(new PhotoPngMessageHandler("a"));
                channel.pipeline().addLast(new PhotoJpgMessageHandler("a"));
                channel.pipeline().addLast(new SimpleChannelInboundHandler() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object byteBuf) throws Exception {
                        System.out.println("收到数据：" + byteBuf);
                    }

                });
            }
        });
        Channel channel = bootstrap.bind(8888).sync().channel();
    }

}
