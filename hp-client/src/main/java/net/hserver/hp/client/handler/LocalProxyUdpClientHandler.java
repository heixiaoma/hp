package net.hserver.hp.client.handler;

import com.google.protobuf.ByteString;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import net.hserver.hp.common.protocol.HpMessageData;

public class LocalProxyUdpClientHandler extends
        SimpleChannelInboundHandler<DatagramPacket> {
    private final HpClientHandler proxyHandler;
    private final String remoteChannelId;
    private ChannelHandlerContext ctx;

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        proxyHandler.getCtx().channel().config().setAutoRead(ctx.channel().isWritable());
        super.channelWritabilityChanged(ctx);
    }
    public LocalProxyUdpClientHandler(HpClientHandler hpClientHandler, String remoteChannelId) {
        this.proxyHandler = hpClientHandler;
        this.remoteChannelId = remoteChannelId;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
    }


    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable
            cause) throws Exception {
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        HpMessageData.HpMessage.Builder messageBuild = HpMessageData.HpMessage.newBuilder();
        messageBuild.setType(HpMessageData.HpMessage.HpMessageType.DATA)
                .setData(ByteString.copyFrom(ByteBufUtil.getBytes(msg.content())))
                .setMetaData(HpMessageData.HpMessage.MetaData.newBuilder()
                        .setType(HpMessageData.HpMessage.MessageType.UDP)
                        .setChannelId(remoteChannelId).build());
        proxyHandler.getCtx().writeAndFlush(messageBuild.build());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        HpMessageData.HpMessage build = HpMessageData.HpMessage.newBuilder().
                setMetaData(HpMessageData.HpMessage.MetaData.newBuilder()
                        .setType(HpMessageData.HpMessage.MessageType.UDP)
                        .setChannelId(remoteChannelId).build()).setType(HpMessageData.HpMessage.HpMessageType.DISCONNECTED).build();
        proxyHandler.getCtx().writeAndFlush(build);
    }
}
