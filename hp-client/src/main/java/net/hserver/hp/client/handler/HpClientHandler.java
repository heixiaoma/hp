package net.hserver.hp.client.handler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;
import net.hserver.hp.client.HpClient;
import net.hserver.hp.client.net.TcpConnection;
import net.hserver.hp.common.exception.HpException;
import net.hserver.hp.common.handler.HpCommonHandler;
import net.hserver.hp.common.protocol.HpMessage;
import net.hserver.hp.common.protocol.HpMessageType;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author hxm
 */
public class HpClientHandler extends HpCommonHandler {

    private int port;
    private String password;
    private String username;
    private String proxyAddress;
    private int proxyPort;

    private ConcurrentHashMap<String, HpCommonHandler> channelHandlerMap = new ConcurrentHashMap<>();
    private ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public HpClientHandler(int port, String username, String password, String proxyAddress, int proxyPort) {
        this.port = port;
        this.password = password;
        this.username = username;
        this.proxyAddress = proxyAddress;
        this.proxyPort = proxyPort;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // register client information
        HpMessage message = new HpMessage();
        message.setType(HpMessageType.REGISTER);
        HashMap<String, Object> metaData = new HashMap<>();
        metaData.put("port", port);
        metaData.put("username", username);
        metaData.put("password", password);
        message.setMetaData(metaData);
        ctx.writeAndFlush(message);
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        HpMessage message = (HpMessage) msg;
        if (message.getType() == HpMessageType.REGISTER_RESULT) {
            processRegisterResult(message);
        } else if (message.getType() == HpMessageType.CONNECTED) {
            processConnected(message);
        } else if (message.getType() == HpMessageType.DISCONNECTED) {
            processDisconnected(message);
        } else if (message.getType() == HpMessageType.DATA) {
            processData(message);
        } else if (message.getType() == HpMessageType.KEEPALIVE) {
            // 心跳包, 不处理
        } else {
            throw new HpException("Unknown type: " + message.getType());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        channelGroup.close();
        HpClient.setMsg("与HP服务器的连接断开，请重新启动！");
    }

    /**
     * if Message.getType() == HpMessageType.REGISTER_RESULT
     */
    private void processRegisterResult(HpMessage message) {
        if ((Boolean) message.getMetaData().get("success")) {
            HpClient.setMsg("成功注册到HP服务器");
        } else {
            //认证错误的，不在重新连接
            if (message.getMetaData().toString().contains("用户非法")) {
                HpClient.isAuth = false;
            }
            HpClient.setMsg("注册失败: " + message.getMetaData().get("reason"));
            if (message.getMetaData().toString().contains("端口占用")) {
                HpClient.isAuth = false;
                HpClient.setMsg("重启APP吧: " + message.getMetaData().get("reason"));
            }
            ctx.close();
        }
    }

    /**
     * if HpMessage.getType() == HpMessageType.CONNECTED
     */
    private void processConnected(final HpMessage message) throws Exception {

        try {
            final HpClientHandler thisHandler = this;
            TcpConnection localConnection = new TcpConnection();
            localConnection.connect(proxyAddress, proxyPort, new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    LocalProxyHandler localProxyHandler = new LocalProxyHandler(thisHandler, message.getMetaData().get("channelId").toString());
                    ch.pipeline().addLast(new ByteArrayDecoder(), new ByteArrayEncoder(), localProxyHandler);

                    channelHandlerMap.put(message.getMetaData().get("channelId").toString(), localProxyHandler);
                    channelGroup.add(ch);
                }
            });
        } catch (Exception e) {
            HpMessage msg = new HpMessage();
            msg.setType(HpMessageType.DISCONNECTED);
            HashMap<String, Object> metaData = new HashMap<>();
            metaData.put("channelId", msg.getMetaData().get("channelId"));
            msg.setMetaData(metaData);
            ctx.writeAndFlush(msg);
            channelHandlerMap.remove(msg.getMetaData().get("channelId"));
            throw e;
        }
    }

    /**
     * if HpMessage.getType() == HpMessageType.DISCONNECTED
     */
    private void processDisconnected(HpMessage message) {
        String channelId = message.getMetaData().get("channelId").toString();
        HpCommonHandler handler = channelHandlerMap.get(channelId);
        if (handler != null) {
            handler.getCtx().close();
            channelHandlerMap.remove(channelId);
        }
    }

    /**
     * if HpMessage.getType() == HpMessageType.DATA
     */
    private void processData(HpMessage message) {
        String channelId = message.getMetaData().get("channelId").toString();
        HpCommonHandler handler = channelHandlerMap.get(channelId);
        if (handler != null) {
            ChannelHandlerContext ctx = handler.getCtx();
            ctx.writeAndFlush(message.getData());
        }
    }
}
