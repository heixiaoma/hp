package net.hserver.hp.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 后端处理程序
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {


    List<ChannelHandlerContext> data = new ArrayList<>();


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
    }
}