package net.hserver.hp.server.protocol;

import cn.hserver.core.ioc.annotation.Bean;
import cn.hserver.core.ioc.annotation.Order;
import cn.hserver.plugin.web.protocol.DispatchHttp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

@Order(7)
@Bean
public class HttpDispatcher extends DispatchHttp {
    private static final Logger log = LoggerFactory.getLogger(HttpDispatcher.class);

    @Override
    public boolean dispatcher(ChannelHandlerContext ctx, ChannelPipeline pipeline, byte[] headers) {
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().localAddress();
        if (socketAddress.getPort() == 9090) {
            return super.dispatcher(ctx, pipeline, headers);
        }
        return false;
    }
}
