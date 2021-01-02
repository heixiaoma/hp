package net.hserver.hp.client;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import net.hserver.hp.client.handler.HpClientHandler;
import net.hserver.hp.client.net.TcpConnection;
import net.hserver.hp.common.codec.HpMessageDecoder;
import net.hserver.hp.common.codec.HpMessageEncoder;

import java.io.IOException;

/**
 * Created by wucao on 2019/2/27.
 */
public class HpClient {

    public void connect(String serverAddress, int serverPort, String password, int remotePort, String proxyAddress, int proxyPort) throws IOException, InterruptedException {
        TcpConnection hpConnection = new TcpConnection();
        ChannelFuture future = hpConnection.connect(serverAddress, serverPort, new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                HpClientHandler hpClientHandler = new HpClientHandler(remotePort, password,
                        proxyAddress, proxyPort);
                ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4),
                        new HpMessageDecoder(), new HpMessageEncoder(),
                        new IdleStateHandler(60, 30, 0), hpClientHandler);
            }
        });

        // channel close retry connect
        future.addListener(future1 -> new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        connect(serverAddress, serverPort, password, remotePort, proxyAddress, proxyPort);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }.start());
    }
}
