package net.hserver.hp.proxy.protocol;

import cn.hserver.core.interfaces.ProtocolDispatcherAdapter;
import cn.hserver.core.ioc.annotation.Bean;
import cn.hserver.core.ioc.annotation.Order;
import cn.hserver.core.server.util.PropUtil;
import cn.hserver.plugin.web.context.WebConstConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.traffic.GlobalChannelTrafficShapingHandler;
import io.netty.handler.traffic.GlobalTrafficShapingHandler;
import net.hserver.hp.common.codec.HpMessageDecoder;
import net.hserver.hp.common.codec.HpMessageEncoder;
import net.hserver.hp.proxy.config.CostConfig;
import net.hserver.hp.proxy.handler.HpServerHandler;

import java.net.InetSocketAddress;

@Order(6)
@Bean
public class HpProtocolDispatcher implements ProtocolDispatcherAdapter {

    private Integer port;

    //判断HP头
    @Override
    public boolean dispatcher(ChannelHandlerContext ctx, ChannelPipeline channelPipeline, byte[] bytes) {
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().localAddress();
        if (port == null) {
            port = PropUtil.getInstance().getInt("port");
        }
        if (socketAddress.getPort() == port) {
            //todo 添加流量整形控制
            channelPipeline.addLast(new GlobalTrafficShapingHandler(ctx.executor(), 1024 * 512, 1024 * 512) );
            channelPipeline.addLast(new IdleStateHandler(60, 30, 0));
            channelPipeline.addLast(new HpMessageDecoder());
            channelPipeline.addLast(new HpMessageEncoder());
            channelPipeline.addLast(WebConstConfig.BUSINESS_EVENT, new HpServerHandler());
            return true;
        }
        return false;
    }
}
