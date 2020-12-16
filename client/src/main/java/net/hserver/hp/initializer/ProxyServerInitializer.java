package net.hserver.hp.initializer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import net.hserver.hp.handler.ProxyServerHandler;

/**
 * @author hxm
 */
public class ProxyServerInitializer extends ChannelInitializer<SocketChannel > {

    @Override
    protected void initChannel(SocketChannel  ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast( new LoggingHandler(LogLevel.DEBUG));
        pipeline.addLast("ServerHandler", new ProxyServerHandler());
    }
}