package net.hserver.hp.client.handler;


import com.google.protobuf.ByteString;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import net.hserver.hp.common.handler.HpAbsHandler;
import net.hserver.hp.common.handler.HpCommonHandler;
import net.hserver.hp.common.protocol.HpMessageData;

/**
 * @author hxm
 */
public class LocalProxyHandler extends SimpleChannelInboundHandler<byte[]>  {

    private final HpClientHandler proxyHandler;
    private final String remoteChannelId;
    private ChannelHandlerContext ctx;

    public LocalProxyHandler(HpClientHandler hpClientHandler, String remoteChannelId) {
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
    public void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
        HpMessageData.HpMessage.Builder messageBuild = HpMessageData.HpMessage.newBuilder();
        messageBuild.setType(HpMessageData.HpMessage.HpMessageType.DATA)
                .setData(ByteString.copyFrom(msg))
                .setMetaData(HpMessageData.HpMessage.MetaData.newBuilder()
                        .setChannelId(remoteChannelId).build());
        proxyHandler.getCtx().writeAndFlush(messageBuild.build());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        HpMessageData.HpMessage build = HpMessageData.HpMessage.newBuilder().
                setMetaData(HpMessageData.HpMessage.MetaData.newBuilder().
                        setChannelId(remoteChannelId).build()).setType(HpMessageData.HpMessage.HpMessageType.DISCONNECTED).build();
        proxyHandler.getCtx().writeAndFlush(build);
    }


}
