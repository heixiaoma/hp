package net.hserver.hp.client;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.hserver.hp.client.handler.HpClientHandler;
import net.hserver.hp.client.net.TcpConnection;
import net.hserver.hp.common.codec.HpMessageDecoder;
import net.hserver.hp.common.codec.HpMessageEncoder;
import net.hserver.hp.common.protocol.HpMessage;

import java.io.IOException;

/**
 * @author hxm
 */
public class HpClient {

    private CallMsg callMsg;

    private ChannelFuture future;

    public HpClient(CallMsg callMsg) {
        this.callMsg = callMsg;
    }

    public void connect(String serverAddress, int serverPort, String username, String password, int remotePort, String proxyAddress, int proxyPort) throws IOException, InterruptedException {
        if (future != null) {
            future.channel().close();
            future = null;
        }
        try {
            TcpConnection hpConnection = new TcpConnection();
            future = hpConnection.connect(serverAddress, serverPort, new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    HpClientHandler hpClientHandler = new HpClientHandler(remotePort, username, password,
                            proxyAddress, proxyPort, callMsg);
                    ch.pipeline().addLast(
                            new IdleStateHandler(60, 30, 0),
                            new HpMessageDecoder(HpMessage.class), new HpMessageEncoder(HpMessage.class),
                            hpClientHandler);
                }
            });

            //兼容Android不用拉米大
            future.addListener(new GenericFutureListener() {
                @Override
                public void operationComplete(Future future) throws Exception {
                    callMsg.message("断开了连接");
                }
            });

        } catch (Throwable e) {
            e.printStackTrace();
            callMsg.message(e.getMessage());
        }
    }

    public boolean getStatus() {
        return future != null && future.channel().isActive();
    }

    public void close() {
        if (future != null) {
            future.channel().close();
        }

    }

}
