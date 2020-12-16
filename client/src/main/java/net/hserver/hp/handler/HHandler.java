package net.hserver.hp.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import net.hserver.hp.bean.Data;
import net.hserver.hp.utils.ByteBufUtil;

/**
 * 后端处理程序
 * @author hxm
 */
public class HHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private final Channel inboundChannel;

    public HHandler(Channel inboundChannel) {
        this.inboundChannel = inboundChannel;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.read();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("--" + msg);
        inboundChannel.writeAndFlush(new Data(true, ByteBufUtil.byteBufToBytes(msg))).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                System.out.println("客服端发送成功");
                ctx.channel().read();
            } else {
                System.out.println("客服端发送失败");
                future.channel().close();
            }
        });
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("关闭客服端");
        BaseHandler.closeOnFlush(inboundChannel);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        BaseHandler.closeOnFlush(ctx.channel());
    }
}