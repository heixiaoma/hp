package net.hserver.hp.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import net.hserver.hp.common.codec.HpMessageDecoder;
import net.hserver.hp.common.codec.HpMessageEncoder;
import net.hserver.hp.server.handler.HpServerHandler;


/**
 * @author hxm
 */
public class HpServer {

    public void start(int port, String password) throws InterruptedException {

        TcpServer hpClientServer = new TcpServer();
        hpClientServer.bind(port, new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch)
                    throws Exception {
                HpServerHandler hpServerHandler = new HpServerHandler(password);
                ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4),
                        new HpMessageDecoder(), new HpMessageEncoder(),
                        new IdleStateHandler(60, 30, 0), hpServerHandler);
            }
        });
    }
}
