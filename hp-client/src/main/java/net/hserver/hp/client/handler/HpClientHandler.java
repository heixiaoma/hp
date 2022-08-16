package net.hserver.hp.client.handler;


import com.google.protobuf.ByteString;
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
import net.hserver.hp.common.protocol.HpMessageData;

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
        HpMessageData.HpMessage.Builder messageBuild = HpMessageData.HpMessage.newBuilder();
        messageBuild.setType(HpMessageData.HpMessage.HpMessageType.REGISTER);
        HpMessageData.HpMessage.MetaData.Builder metaDataBuild = HpMessageData.HpMessage.MetaData.newBuilder();
        metaDataBuild.setPort(port);
        metaDataBuild.setUsername(username);
        metaDataBuild.setPassword(password);
        messageBuild.setMetaData(metaDataBuild.build());
        ctx.writeAndFlush(messageBuild.build());
        super.channelActive(ctx);
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HpMessageData.HpMessage message) throws Exception {
        if (message.getType() == HpMessageData.HpMessage.HpMessageType.REGISTER_RESULT) {
            processRegisterResult(message);
        } else if (message.getType() == HpMessageData.HpMessage.HpMessageType.CONNECTED) {
            processConnected(message);
        } else if (message.getType() == HpMessageData.HpMessage.HpMessageType.DISCONNECTED) {
            processDisconnected(message);
        } else if (message.getType() == HpMessageData.HpMessage.HpMessageType.DATA) {
            processData(message);
        } else if (message.getType() == HpMessageData.HpMessage.HpMessageType.KEEPALIVE) {
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
    private void processRegisterResult(HpMessageData.HpMessage message) {
        if (message.getMetaData().getSuccess()) {
            callMsg.message(message.getMetaData().getReason());
        } else {
            callMsg.message(message.getMetaData().getReason());
            ctx.close();
        }
    }

    /**
     * if HpMessage.getType() == HpMessageType.CONNECTED
     */
    private void processConnected(final HpMessageData.HpMessage message) throws Exception {
        try {
            final HpClientHandler thisHandler = this;
            TcpConnection localConnection = new TcpConnection();
            localConnection.connect(proxyAddress, proxyPort, new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    LocalProxyHandler localProxyHandler = new LocalProxyHandler(thisHandler, message.getMetaData().getChannelId());
                    ch.pipeline().addLast(new ByteArrayDecoder(), new ByteArrayEncoder(), localProxyHandler);
                    channelHandlerMap.put(message.getMetaData().getChannelId(), localProxyHandler);
                    channelGroup.add(ch);
                }
            });
        } catch (Exception e) {
            HpMessageData.HpMessage.Builder messageBuild = HpMessageData.HpMessage.newBuilder();
            messageBuild.setType(HpMessageData.HpMessage.HpMessageType.DISCONNECTED);
            HpMessageData.HpMessage.MetaData.Builder metaDataBuild = HpMessageData.HpMessage.MetaData.newBuilder();
            metaDataBuild.setChannelId(message.getMetaData().getChannelId());
            messageBuild.setMetaData(metaDataBuild.build());
            ctx.writeAndFlush(messageBuild.build());
            channelHandlerMap.remove(message.getMetaData().getChannelId());
            callMsg.message(e.getMessage());
            throw e;
        }
    }

    /**
     * if HpMessage.getType() == HpMessageType.DISCONNECTED
     */
    private void processDisconnected(HpMessageData.HpMessage message) {
        String channelId = message.getMetaData().getChannelId();
        HpAbsHandler handler = channelHandlerMap.get(channelId);
        if (handler != null) {
            handler.getCtx().close();
            channelHandlerMap.remove(channelId);
        }
    }

    /**
     * if HpMessage.getType() == HpMessageType.DATA
     */
    private void processData(HpMessageData.HpMessage message) {
        String channelId = message.getMetaData().getChannelId();
        HpAbsHandler handler = channelHandlerMap.get(channelId);
        if (handler != null&&handler.getCtx()!=null) {
            ChannelHandlerContext ctx = handler.getCtx();
            ctx.writeAndFlush(message.getData().toByteArray());
        }
    }
}
