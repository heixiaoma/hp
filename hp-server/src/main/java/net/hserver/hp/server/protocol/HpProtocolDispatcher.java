package net.hserver.hp.server.protocol;

import cn.hserver.plugin.web.context.WebConstConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;
import net.hserver.hp.common.codec.HpMessageDecoder;
import net.hserver.hp.common.codec.HpMessageEncoder;
import net.hserver.hp.common.protocol.HpMessage;
import net.hserver.hp.server.handler.HpServerHandler;
import cn.hserver.core.interfaces.ProtocolDispatcherAdapter;
import cn.hserver.core.ioc.annotation.Bean;
import cn.hserver.core.ioc.annotation.Order;

@Order(6)
@Bean
public class HpProtocolDispatcher implements ProtocolDispatcherAdapter {

    //判断HP头
    @Override
    public boolean dispatcher(ChannelHandlerContext channelHandlerContext, ChannelPipeline channelPipeline, byte[] bytes) {
        if (bytes[3] == 'H' && bytes[7] == 'P') {
            channelPipeline.addLast(new IdleStateHandler(60, 30, 0));
            channelPipeline.addLast(new HpMessageDecoder());
            channelPipeline.addLast(new HpMessageEncoder(HpMessage.class));
            channelPipeline.addLast(WebConstConfig.BUSINESS_EVENT,new HpServerHandler());
            return true;
        }
        return false;
    }
}