package net.hserver.hp.client.handler;


import com.google.protobuf.ByteString;
import io.netty.channel.ChannelHandlerContext;
import net.hserver.hp.common.handler.HpAbsHandler;
import net.hserver.hp.common.handler.HpCommonHandler;
import net.hserver.hp.common.protocol.HpMessageData;

/**
 * @author hxm
 */
public class LocalProxyHandler extends HpAbsHandler {

    private HpCommonHandler proxyHandler;
    private String remoteChannelId;

    public LocalProxyHandler(HpCommonHandler proxyHandler, String remoteChannelId) {
        this.proxyHandler = proxyHandler;
        this.remoteChannelId = remoteChannelId;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] data = (byte[]) msg;
        HpMessageData.HpMessage.Builder messageBuild = HpMessageData.HpMessage.newBuilder();
        messageBuild.setType(HpMessageData.HpMessage.HpMessageType.DATA)
                .setData(ByteString.copyFrom(data))
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
