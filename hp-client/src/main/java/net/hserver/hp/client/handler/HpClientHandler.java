package net.hserver.hp.client.handler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;
import net.hserver.hp.client.CallMsg;
import net.hserver.hp.client.net.TcpConnection;
import net.hserver.hp.common.exception.HpException;
import net.hserver.hp.common.handler.HpAbsHandler;
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
    private CallMsg callMsg;
    private ConcurrentHashMap<String, HpAbsHandler> channelHandlerMap = new ConcurrentHashMap<>();
    private ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public HpClientHandler(int port, String username, String password, String proxyAddress, int proxyPort, CallMsg callMsg) {
        this.port = port;
        this.password = password;
        this.username = username;
        this.proxyAddress = proxyAddress;
        this.proxyPort = proxyPort;
        this.callMsg = callMsg;
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
    protected void channelRead0(ChannelHandlerContext ctx, HpMessage message) throws Exception {
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
        callMsg.message("与HP服务器断开了连接");
    }
    /**
     * if Message.getType() == HpMessageType.REGISTER_RESULT
     */
    private void processRegisterResult(HpMessage message) {
        if ((Boolean) message.getMetaData().get("success")) {
            callMsg.message(message.getMetaData().get("reason").toString());
        } else {
            callMsg.message(message.getMetaData().get("reason").toString());
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
            metaData.put("channelId", message.getMetaData().get("channelId"));
            msg.setMetaData(metaData);
            ctx.writeAndFlush(msg);
            channelHandlerMap.remove(message.getMetaData().get("channelId"));
            callMsg.message(e.getMessage());
            throw e;
        }
    }

    /**
     * if HpMessage.getType() == HpMessageType.DISCONNECTED
     */
    private void processDisconnected(HpMessage message) {
        String channelId = message.getMetaData().get("channelId").toString();
        HpAbsHandler handler = channelHandlerMap.get(channelId);
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
        HpAbsHandler handler = channelHandlerMap.get(channelId);
        if (handler != null) {
            ChannelHandlerContext ctx = handler.getCtx();
            ctx.writeAndFlush(message.getData());
        }
    }
}
