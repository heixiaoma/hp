package net.hserver.hp.common;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.hserver.hp.common.codec.PhotoMessageDecoder;
import net.hserver.hp.common.codec.PhotoMessageEncoder;

import java.io.FileInputStream;

public class Test {

    public static void main(String[] args) throws Exception {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup()).channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                channel.pipeline().addLast(new PhotoMessageDecoder("false", "", "i"));
                channel.pipeline().addLast(new PhotoMessageEncoder("false", "", "o"));
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
