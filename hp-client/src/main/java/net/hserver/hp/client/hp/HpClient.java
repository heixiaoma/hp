package net.hserver.hp.client.hp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.hserver.hp.client.handler.HpClientHandler;
import net.hserver.hp.client.util.NamedThreadFactory;
import net.hserver.hp.common.codec.HpMessageDecoder;
import net.hserver.hp.common.codec.HpMessageEncoder;

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

    public void connect(String serverAddress, int serverPort, String username, String password, String domain, int remotePort, String proxyAddress, int proxyPort) throws IOException, InterruptedException {
        if (future != null) {
            future.channel().close();
            future = null;
        }
        try {
            NioEventLoopGroup workerGroup = new NioEventLoopGroup(new NamedThreadFactory("HP-Client-Worker"));
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    HpClientHandler hpClientHandler = new HpClientHandler(remotePort, username, password, domain,
                            proxyAddress, proxyPort, callMsg);
                    ch.pipeline().addLast(
                            new HpMessageDecoder(),
                            new HpMessageEncoder(),
                            hpClientHandler
                    );
                }
            });
            Channel channel = b.connect(serverAddress, serverPort).sync().channel();
            future = channel.closeFuture().addListener(future -> workerGroup.shutdownGracefully());
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
