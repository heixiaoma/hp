package net.hserver.hp.proxy.handler;

import com.google.protobuf.ByteString;
import io.netty.channel.ChannelHandlerContext;
import net.hserver.hp.common.handler.HpAbsHandler;
import net.hserver.hp.common.handler.HpCommonHandler;
import net.hserver.hp.common.protocol.HpMessageData;

/**
 * @author hxm
 */
public class RemoteProxyHandler extends HpAbsHandler {

    private final HpCommonHandler proxyHandler;

    private final TcpServer tcpServer;

    public RemoteProxyHandler(HpCommonHandler proxyHandler, TcpServer tcpServer) {
        this.proxyHandler = proxyHandler;
        this.tcpServer = tcpServer;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        tcpServer.addConnectNum();
        HpMessageData.HpMessage.Builder messageBuilder = HpMessageData.HpMessage.newBuilder();
        messageBuilder.setType(HpMessageData.HpMessage.HpMessageType.CONNECTED);
        messageBuilder.setMetaData(HpMessageData.HpMessage.MetaData.newBuilder().setChannelId(ctx.channel().id().asLongText()).build());
        proxyHandler.getCtx().writeAndFlush(messageBuilder.build());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        HpMessageData.HpMessage.Builder messageBuilder = HpMessageData.HpMessage.newBuilder();
        messageBuilder.setType(HpMessageData.HpMessage.HpMessageType.DISCONNECTED);
        messageBuilder.setMetaData(HpMessageData.HpMessage.MetaData.newBuilder().setChannelId(ctx.channel().id().asLongText()).build());
        proxyHandler.getCtx().writeAndFlush(messageBuilder.build());
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
        tcpServer.addPackNum();
        HpMessageData.HpMessage.Builder messageBuilder = HpMessageData.HpMessage.newBuilder();
        messageBuilder.setType(HpMessageData.HpMessage.HpMessageType.DATA);
        messageBuilder.setMetaData(HpMessageData.HpMessage.MetaData.newBuilder().setChannelId(ctx.channel().id().asLongText()).build());
        messageBuilder.setData(ByteString.copyFrom(msg));
        proxyHandler.getCtx().writeAndFlush(messageBuilder.build());
    }
}
