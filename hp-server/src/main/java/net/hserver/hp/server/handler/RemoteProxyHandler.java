package net.hserver.hp.server.handler;

import com.google.protobuf.ByteString;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import net.hserver.hp.common.handler.HpAbsHandler;
import net.hserver.hp.common.handler.HpCommonHandler;
import net.hserver.hp.common.protocol.HpMessageOuterClass;

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
        HpMessageOuterClass.HpMessage.Builder messageBuilder = HpMessageOuterClass.HpMessage.newBuilder();
        messageBuilder.setType(HpMessageOuterClass.HpMessage.HpMessageType.CONNECTED);
        messageBuilder.setMetaData(HpMessageOuterClass.HpMessage.MetaData.newBuilder().setChannelId(ctx.channel().id().asLongText()).build());
        proxyHandler.getCtx().writeAndFlush(messageBuilder.build());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        HpMessageOuterClass.HpMessage.Builder messageBuilder = HpMessageOuterClass.HpMessage.newBuilder();
        messageBuilder.setType(HpMessageOuterClass.HpMessage.HpMessageType.DISCONNECTED);
        messageBuilder.setMetaData(HpMessageOuterClass.HpMessage.MetaData.newBuilder().setChannelId(ctx.channel().id().asLongText()).build());
        proxyHandler.getCtx().writeAndFlush(messageBuilder.build());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            byte[] data = (byte[]) msg;
            tcpServer.addPackNum();
            HpMessageOuterClass.HpMessage.Builder messageBuilder = HpMessageOuterClass.HpMessage.newBuilder();
            messageBuilder.setType(HpMessageOuterClass.HpMessage.HpMessageType.DATA);
            messageBuilder.setMetaData(HpMessageOuterClass.HpMessage.MetaData.newBuilder().setChannelId(ctx.channel().id().asLongText()).build());
            messageBuilder.setData(ByteString.copyFrom(data));
            proxyHandler.getCtx().writeAndFlush(messageBuilder.build());
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
