package net.hserver.hp.server.protocol;

import cn.hserver.plugin.web.context.WebConstConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import net.hserver.hp.server.handler.proxy.FrontendHandler;
import cn.hserver.core.interfaces.ProtocolDispatcherAdapter;
import cn.hserver.core.ioc.annotation.Bean;
import cn.hserver.core.ioc.annotation.Order;

import java.net.InetSocketAddress;

/**
 * 优先级要调整到自己的管理后台http协议的之上，都是http协议，所以这里需要判断是否是80
 */
@Order(1)
@Bean
public class HpWebProxyProtocolDispatcher implements ProtocolDispatcherAdapter {

    //判断HP头
    @Override
    public boolean dispatcher(ChannelHandlerContext ctx, ChannelPipeline channelPipeline, byte[] headers) {
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().localAddress();
        if (socketAddress.getPort() == 80) {
            if (this.isHttp(headers[0], headers[1])) {
                httpProxyHandler(ctx);
                return true;
            }
        }
        return false;
    }

    public static void httpProxyHandler(ChannelHandlerContext ctx) {
        ChannelPipeline pipeline = ctx.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
        pipeline.addLast(WebConstConfig.BUSINESS_EVENT,new FrontendHandler());

    }

    private boolean isHttp(int magic1, int magic2) {
        return magic1 == 71 && magic2 == 69 || magic1 == 80 && magic2 == 79 || magic1 == 80 && magic2 == 85 || magic1 == 72 && magic2 == 69 || magic1 == 79 && magic2 == 80 || magic1 == 80 && magic2 == 65 || magic1 == 68 && magic2 == 69 || magic1 == 84 && magic2 == 82 || magic1 == 67 && magic2 == 79;
    }

}