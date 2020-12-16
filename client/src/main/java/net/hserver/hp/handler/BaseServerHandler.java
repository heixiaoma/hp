package net.hserver.hp.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import net.hserver.hp.bean.Data;
import net.hserver.hp.server.ProxyServer;

import java.util.ArrayList;
import java.util.List;

/**
 * 后端处理程序
 *
 * @author hxm
 */
public class BaseServerHandler extends SimpleChannelInboundHandler<Data> {

    /**
     * 客服端的连接
     */
    public static ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        BaseServerHandler.ctx = ctx;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Data msg) throws Exception {
        if (msg.isFlag() && ProxyServerHandler.ctx != null) {
            System.out.println("BaseServerHandler发给真实用户：" + msg);
            ProxyServerHandler.ctx.writeAndFlush(Unpooled.wrappedBuffer(msg.getData()));
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
    }
}