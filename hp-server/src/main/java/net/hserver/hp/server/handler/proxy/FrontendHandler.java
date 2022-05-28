package net.hserver.hp.server.handler.proxy;

import com.google.common.net.HttpHeaders;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequest;
import net.hserver.hp.server.handler.HpServerHandler;
import top.hserver.core.server.handlers.BuildResponse;

public class FrontendHandler extends ChannelInboundHandlerAdapter {

    private Channel outboundChannel;


    static void closeOnFlush(Channel ch) {
        if (ch.isActive()) {
            ch.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }

    private void read(final ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest httpRequest = (FullHttpRequest) msg;
            outboundChannel.writeAndFlush(msg);
        } else {
            closeOnFlush(ctx.channel());
        }
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) {
        if (outboundChannel == null) {
            HttpRequest httpRequest = (HttpRequest) msg;
            String host = httpRequest.headers().get("Host");
            /**
             * host为空直接断开
             */
            if (host == null) {
                ctx.writeAndFlush(BuildResponse.buildString("host检查异常"));
                ctx.close();
                return;
            }
            //提取用户名，约定二级域名就是用户的账号
            String[] split = host.split("\\.");
            String username = split[0];
            final Integer[] userPort = {-1};
            HpServerHandler.CURRENT_STATUS.forEach((k, v) -> {
                if (v.equals(username)) {
                    userPort[0] = Integer.parseInt(k);
                }
            });
            //如果为负说明用户不存在，将他删除掉
            if (userPort[0] == -1) {
                ctx.writeAndFlush(BuildResponse.buildString("用户不在线"));
                ctx.close();
                return;
            }

            final Channel inboundChannel = ctx.channel();

            Bootstrap b = new Bootstrap();
            b.group(inboundChannel.eventLoop());
            b.channel(NioSocketChannel.class).handler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) {
                    ch.pipeline().addLast(new HttpClientCodec(), new HttpObjectAggregator(Integer.MAX_VALUE));
                    ch.pipeline().addLast(new BackendHandler(inboundChannel));
                }
            });
            ChannelFuture f = b.connect("127.0.0.1", userPort[0]).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    future.channel().writeAndFlush(msg);
                } else {
                    future.channel().close();
                }
            });
            outboundChannel = f.channel();
        } else {
            read(ctx, msg);
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
        cause.printStackTrace();
        closeOnFlush(ctx.channel());
    }
}