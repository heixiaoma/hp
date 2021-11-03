package net.hserver.hp.server.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import net.hserver.hp.common.codec.HpMessageDecoder;
import net.hserver.hp.common.codec.HpMessageEncoder;
import net.hserver.hp.server.handler.HpServerHandler;
import top.hserver.core.interfaces.ProtocolDispatcherAdapter;
import top.hserver.core.ioc.annotation.Bean;
import top.hserver.core.ioc.annotation.Order;

@Order(6)
@Bean
public class HpProtocolDispatcher implements ProtocolDispatcherAdapter {

    private HpServerHandler hpServerHandler = new HpServerHandler();

    //000630001000
    @Override
    public boolean dispatcher(ChannelHandlerContext channelHandlerContext, ChannelPipeline channelPipeline, byte[] bytes) {
        if (bytes[0] == 0 && bytes[1] == 0 && bytes[2] == 0 && bytes[3] == 63 && bytes[4] == 0 && bytes[5] == 0 && bytes[6] == 0) {
            channelPipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4),
                    new HpMessageDecoder(), new HpMessageEncoder(),
                    new IdleStateHandler(60, 30, 0), hpServerHandler);
            return true;
        }
        return false;
    }
}