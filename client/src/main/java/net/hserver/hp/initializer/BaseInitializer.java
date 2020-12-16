package net.hserver.hp.initializer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import net.hserver.hp.bean.Data;
import net.hserver.hp.codec.DataDecoder;
import net.hserver.hp.codec.DataEncoder;
import net.hserver.hp.handler.BaseHandler;

/**
 * @author hxm
 */
public class BaseInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel  ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new DataDecoder(Data.class))
                .addLast(new DataEncoder(Data.class))
                .addLast("BaseHandler", new BaseHandler());
    }

}