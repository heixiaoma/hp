package net.hserver.hp.proxy.handler.proxy;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;
import net.hserver.hp.proxy.config.CostConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FrontendHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(FrontendHandler.class);

    private final Integer port;
    private Channel outboundChannel;


    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        log.debug("限制操作，让WEB代理两个通道实现同步读写 开关状态:{}",ctx.channel().isWritable());
        ctx.channel().config().setAutoRead(ctx.channel().isWritable());
        outboundChannel.config().setAutoRead(ctx.channel().isWritable());
    }


    public FrontendHandler(Integer port) {
        this.port = port;
    }

    static void closeOnFlush(Channel ch) {
        if (ch.isActive()) {
            ch.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }


    public void write(ChannelHandlerContext ctx, Object msg) {
        outboundChannel.writeAndFlush(msg).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                ctx.channel().read();
            } else {
                future.channel().close();
                ReferenceCountUtil.release(msg);
            }
        });
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) {
        if (outboundChannel != null) {
            if (outboundChannel.isActive()) {
                write(ctx, msg);
            } else {
                outboundChannel.close();
                outboundChannel = null;
                ReferenceCountUtil.release(msg);
            }
        } else {
            final Channel inboundChannel = ctx.channel();
            Bootstrap b = new Bootstrap();
            b.group(inboundChannel.eventLoop());
            b.option(ChannelOption.AUTO_READ, true)
                    .channel(NioSocketChannel.class)
                    .handler(new BackendHandler(inboundChannel));
            b.connect("127.0.0.1", port).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    outboundChannel = future.channel();
                    inboundChannel.read();
                    write(ctx, msg);
                } else {
                    inboundChannel.close();
                    ReferenceCountUtil.release(msg);
                }
            });
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (outboundChannel != null) {
            closeOnFlush(outboundChannel);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("WEB通道 ......",cause);
        closeOnFlush(ctx.channel());
    }
}
