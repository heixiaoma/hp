package net.hserver.hp.handler;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import net.hserver.hp.bean.Data;

/**
 * 后端处理程序
 *
 * @author hxm
 */
public class BaseHandler extends SimpleChannelInboundHandler<Data> {

    private Channel outboundChannel;
    private Channel inboundChannel;

    static void closeOnFlush(Channel ch) {
        if (ch.isActive()) {
            ch.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        inboundChannel = ctx.channel();
        reActive();
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Data msg) throws Exception {
        System.out.println("收到：" + msg);
        if (msg.isFlag()) {
            if (outboundChannel.isActive()) {
                outboundChannel.writeAndFlush(Unpooled.wrappedBuffer(msg.getData())).addListener((ChannelFutureListener) future -> {
                    if (future.isSuccess()) {
                        System.out.println("发送给客服端成功");
                        ctx.channel().read();
                    } else {
                        System.out.println("发送给客服端失败");
                        future.channel().close();
                    }
                });
            }
        }
        if (msg.isActive()) {
            System.out.println("重新激活连接");
            reActive();
        }
    }

    private void reActive(){
        Bootstrap b = new Bootstrap();
        b.group(inboundChannel.eventLoop());
        b.option(ChannelOption.AUTO_READ, true)
                .channel(NioSocketChannel.class)
                .handler(new HHandler(inboundChannel));
        ChannelFuture f = b.connect("47.103.218.193", 9999).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                inboundChannel.read();
            } else {
                inboundChannel.close();
            }
        });
        outboundChannel = f.channel();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (outboundChannel != null) {
            closeOnFlush(outboundChannel);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        closeOnFlush(ctx.channel());
    }
}