package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;
import net.TcpServer;
import net.hserver.hp.common.exception.HpException;
import net.hserver.hp.common.handler.HpCommonHandler;
import net.hserver.hp.common.protocol.HpMessage;
import net.hserver.hp.common.protocol.HpMessageType;

import java.util.HashMap;


/**
 * @author hxm
 */
public class HpServerHandler extends HpCommonHandler {

    private TcpServer remoteConnectionServer = new TcpServer();

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private String password;
    private int port;

    private boolean register = false;

    public HpServerHandler(String password) {
        this.password = password;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        HpMessage hpMessage = (HpMessage) msg;
        if (hpMessage.getType() == HpMessageType.REGISTER) {
            processRegister(hpMessage);
        } else if (register) {
            if (hpMessage.getType() == HpMessageType.DISCONNECTED) {
                processDisconnected(hpMessage);
            } else if (hpMessage.getType() == HpMessageType.DATA) {
                processData(hpMessage);
            } else if (hpMessage.getType() == HpMessageType.KEEPALIVE) {
                // 心跳包, 不处理
            } else {
                throw new HpException("Unknown type: " + hpMessage.getType());
            }
        } else {
            ctx.close();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        remoteConnectionServer.close();
        if (register) {
            System.out.println("Stop server on port: " + port);
        }
    }

    /**
     * if HpMessage.getType() == HpMessageType.REGISTER
     */
    private void processRegister(HpMessage hpMessage) {
        HashMap<String, Object> metaData = new HashMap<>();

        String password = hpMessage.getMetaData().get("password").toString();
        if (this.password != null && !this.password.equals(password)) {
            metaData.put("success", false);
            metaData.put("reason", "Token is wrong");
        } else {
            int port = (int) hpMessage.getMetaData().get("port");

            try {

                HpServerHandler thisHandler = this;
                remoteConnectionServer.bind(port, new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ByteArrayDecoder(), new ByteArrayEncoder(), new RemoteProxyHandler(thisHandler));
                        channels.add(ch);
                    }
                });

                metaData.put("success", true);
                this.port = port;
                register = true;
                System.out.println("Register success, start server on port: " + port);
            } catch (Exception e) {
                metaData.put("success", false);
                metaData.put("reason", e.getMessage());
                e.printStackTrace();
            }
        }

        HpMessage sendBackMessage = new HpMessage();
        sendBackMessage.setType(HpMessageType.REGISTER_RESULT);
        sendBackMessage.setMetaData(metaData);
        ctx.writeAndFlush(sendBackMessage);

        if (!register) {
            System.out.println("Client register error: " + metaData.get("reason"));
            ctx.close();
        }
    }

    /**
     * if HpMessage.getType() == HpMessageType.DATA
     */
    private void processData(HpMessage hpMessage) {
        channels.writeAndFlush(hpMessage.getData(), channel -> channel.id().asLongText().equals(hpMessage.getMetaData().get("channelId")));
    }

    /**
     * if HpMessage.getType() == HpMessageType.DISCONNECTED
     * @param hpMessage
     */
    private void processDisconnected(HpMessage hpMessage) {
        channels.close(channel -> channel.id().asLongText().equals(hpMessage.getMetaData().get("channelId")));
    }
}
