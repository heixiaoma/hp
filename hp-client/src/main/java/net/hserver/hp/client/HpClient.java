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

    private static CallMsg callMsg;

    public static boolean isAuth = true;

    public void connect(String serverAddress, int serverPort, String username, String password, int remotePort, String proxyAddress, int proxyPort) throws IOException, InterruptedException {
        TcpConnection hpConnection = new TcpConnection();
        ChannelFuture future = hpConnection.connect(serverAddress, serverPort, new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                HpClientHandler hpClientHandler = new HpClientHandler(remotePort, username, password,
                        proxyAddress, proxyPort);
                ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4),
                        new HpMessageDecoder(), new HpMessageEncoder(),
                        new IdleStateHandler(60, 30, 0), hpClientHandler);
            }
        });

        // channel close retry connect
        future.addListener(future1 -> new Thread(() -> {
            while (isAuth) {
                try {
                    connect(serverAddress, serverPort, username, password, remotePort, proxyAddress, proxyPort);
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
        }).start());
    }

    public void onMessage(CallMsg callMsg) {
        HpClient.callMsg = callMsg;
    }

    public static void setMsg(String msg) {
        if (msg != null && callMsg != null) {
            callMsg.message(msg);
        }
    }

}
