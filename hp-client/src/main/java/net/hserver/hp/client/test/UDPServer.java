package net.hserver.hp.client.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class UDPServer {
    public static void main(String[] args) throws Exception{
        Bootstrap humServer = new Bootstrap();
        humServer.group(new NioEventLoopGroup())
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .option(ChannelOption.SO_REUSEADDR, true)
                .handler(new SimpleChannelInboundHandler< DatagramPacket >(){
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
                        System.out.println("收到数据："+ msg.sender() +"---"+new String(ByteBufUtil.getBytes(msg.content())));
                        ByteBuf emptyBuffer = Unpooled.buffer();
                        emptyBuffer.writeBytes("udp server".getBytes(StandardCharsets.UTF_8));
                        ctx.writeAndFlush(new DatagramPacket(emptyBuffer, msg.sender()));

                    }
                });
        Channel channel = humServer.bind(7777).sync().channel();
    }

}
