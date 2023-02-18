package net.hserver.hp.proxy.handler;

import com.google.protobuf.ByteString;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.PlatformDependent;
import net.hserver.hp.common.handler.HpAbsHandler;
import net.hserver.hp.common.protocol.HpMessageData;
import net.hserver.hp.proxy.domian.bean.ConnectInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.hserver.hp.proxy.handler.HpServerHandler.CURRENT_STATUS;
import static net.hserver.hp.proxy.handler.HpServerHandler.channels;

/**
 * @author hxm
 */
public class RemoteProxyHandler extends HpAbsHandler {
    private static final Logger log = LoggerFactory.getLogger(RemoteProxyHandler.class);

    private final HpServerHandler proxyHandler;

    private final TcpServer tcpServer;


    public RemoteProxyHandler(HpServerHandler proxyHandler, TcpServer tcpServer) {
        this.proxyHandler = proxyHandler;
        this.tcpServer = tcpServer;
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        if (!ctx.channel().isWritable()) {
            Channel channel = ctx.channel();
            channel.config().setAutoRead(false);
        }
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        boolean writable = channel.isWritable();
        channel.config().setAutoRead(writable);
        //todo 控制HP通道的读取，主要是内网上传服务器导致数据积压
        //获取是否有不可写通道
        Channel channel1 = channels.stream().filter(k->!k.isWritable()).findFirst().orElse(null);
        //所有外部存在不可写通道，hp不能读取
        if (channel1!=null){
            for (ConnectInfo value : CURRENT_STATUS.values()) {
                if (value.getChannel().config().isAutoRead()) {
                    value.getChannel().config().setAutoRead(false);
                }
            }
        }else {
            for (ConnectInfo value : CURRENT_STATUS.values()) {
                if (!value.getChannel().config().isAutoRead()) {
                    value.getChannel().config().setAutoRead(true);
                }
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        tcpServer.addConnectNum();
        HpMessageData.HpMessage.Builder messageBuilder = HpMessageData.HpMessage.newBuilder();
        messageBuilder.setType(HpMessageData.HpMessage.HpMessageType.CONNECTED);
        messageBuilder.setMetaData(HpMessageData.HpMessage.MetaData.newBuilder().setType(HpMessageData.HpMessage.MessageType.TCP).setChannelId(ctx.channel().id().asLongText()).build());
        proxyHandler.getCtx().writeAndFlush(messageBuilder.build());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        HpMessageData.HpMessage.Builder messageBuilder = HpMessageData.HpMessage.newBuilder();
        messageBuilder.setType(HpMessageData.HpMessage.HpMessageType.DISCONNECTED);
        messageBuilder.setMetaData(HpMessageData.HpMessage.MetaData.newBuilder().setChannelId(ctx.channel().id().asLongText()).build());
        HpMessageData.HpMessage build = messageBuilder.build();
        proxyHandler.getCtx().writeAndFlush(build);
    }


    /**
     * 用户穿透完成在外部创建的TCP服务，当有数据时进来，此时包装数据对象返回给内网客服端
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
        tcpServer.addSend((long) msg.length);
        tcpServer.addPackNum();
        HpMessageData.HpMessage.Builder messageBuilder = HpMessageData.HpMessage.newBuilder();
        messageBuilder.setType(HpMessageData.HpMessage.HpMessageType.DATA);
        messageBuilder.setMetaData(HpMessageData.HpMessage.MetaData.newBuilder().setType(HpMessageData.HpMessage.MessageType.TCP).setChannelId(ctx.channel().id().asLongText()).build());
        messageBuilder.setData(ByteString.copyFrom(msg));
        proxyHandler.getCtx().writeAndFlush(messageBuilder.build());
    }
}
