package net.hserver.hp.server.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import net.hserver.hp.common.codec.HpMessageDecoder;
import net.hserver.hp.common.codec.HpMessageEncoder;
import net.hserver.hp.common.protocol.HpMessage;
import net.hserver.hp.server.handler.HpServerHandler;
import top.hserver.core.interfaces.ProtocolDispatcherAdapter;
import top.hserver.core.ioc.annotation.Bean;
import top.hserver.core.ioc.annotation.Order;

@Order(6)
@Bean
public class HpProtocolDispatcher implements ProtocolDispatcherAdapter {

    //判断HP头
    @Override
    public boolean dispatcher(ChannelHandlerContext channelHandlerContext, ChannelPipeline channelPipeline, byte[] bytes) {
        if (bytes[3] == 'H' && bytes[7] == 'P') {
            channelPipeline.addLast(
                    new IdleStateHandler(60, 30, 0),
                    new HpMessageDecoder(HpMessage.class), new HpMessageEncoder(HpMessage.class),
                    new HpServerHandler());
            return true;
        }
        return false;
    }
}