package net.hserver.hp.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import net.hserver.hp.bean.Data;
import net.hserver.hp.server.BaseServer;
import net.hserver.hp.utils.ByteBufUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 公网访问这里被拦截
 *
 * @author hxm
 */
public class ProxyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    public static ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //通知客服端对应的连接重连，TCP协议，比如mysql 连接过来需要客服端回消息，
        // 所以通知客服端重新连接获得回执消息
        ProxyServerHandler.ctx = ctx;
        System.out.println("channelActiveProxy：" + ctx);
        System.out.println("channelActiveProxy：" + BaseServerHandler.ctx.channel().isActive());
//        BaseServerHandler.ctx.writeAndFlush(new Data(true));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("proxy：" + msg);
        BaseServerHandler.ctx.writeAndFlush(new Data(true, ByteBufUtil.byteBufToBytes(msg)));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
    }
}