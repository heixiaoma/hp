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

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class UDPClient {
    public static void main(String[] args) throws Exception {
        Bootstrap humServer = new Bootstrap();
        humServer.group(new NioEventLoopGroup())
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .option(ChannelOption.SO_REUSEADDR, true)
                .handler(new SimpleChannelInboundHandler<DatagramPacket>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
                        System.out.println("来数据了");
                        System.out.println(new String(ByteBufUtil.getBytes(msg.content())));
                    }
                });
        Channel channel = humServer.bind(0).sync().channel();
        while (true) {
            ByteBuf emptyBuffer = Unpooled.buffer();
            emptyBuffer.writeBytes("udp test".getBytes(StandardCharsets.UTF_8));
            channel.writeAndFlush(new DatagramPacket(emptyBuffer, new InetSocketAddress("hp.nsjiasu.com", 45443)));
            System.out.println("发送了");
            Thread.sleep(1000);
        }
    }
}
