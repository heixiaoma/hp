package net.hserver.hp.proxy.handler.proxy;

import com.google.protobuf.ByteString;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import net.hserver.hp.common.handler.HpCommonHandler;
import net.hserver.hp.common.protocol.HpMessageData;
import net.hserver.hp.proxy.handler.TcpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RemoteUdpServerHandler extends
        SimpleChannelInboundHandler<DatagramPacket> {
    private static final Logger log = LoggerFactory.getLogger(RemoteUdpServerHandler.class);

    private final TcpServer tcpServer;

    private final HpCommonHandler proxyHandler;
    public static final AttributeKey<InetSocketAddress> SENDER = AttributeKey.valueOf("Sender");

    public RemoteUdpServerHandler(HpCommonHandler proxyHandler, TcpServer tcpServer) {
        this.tcpServer = tcpServer;
        this.proxyHandler = proxyHandler;
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        log.error("UDP", cause);
        ctx.close();
    }

    /**
     * 外网回来的数据通道被激活通知内网服务器建立客服端
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        tcpServer.addConnectNum();
        HpMessageData.HpMessage.Builder messageBuilder = HpMessageData.HpMessage.newBuilder();
        messageBuilder.setType(HpMessageData.HpMessage.HpMessageType.CONNECTED);
        messageBuilder.setMetaData(HpMessageData.HpMessage.MetaData.newBuilder().setType(HpMessageData.HpMessage.MessageType.UDP).setChannelId(ctx.channel().id().asLongText()).build());
        proxyHandler.getCtx().writeAndFlush(messageBuilder.build());
    }

    /**
     * 外网的UDP数据返回到内网去 由内网客服端去转发
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        tcpServer.addSend((long) msg.content().readableBytes());
        tcpServer.addPackNum();
        HpMessageData.HpMessage.Builder messageBuilder = HpMessageData.HpMessage.newBuilder();
        messageBuilder.setType(HpMessageData.HpMessage.HpMessageType.DATA);
        messageBuilder.setMetaData(HpMessageData.HpMessage.MetaData.newBuilder().setType(HpMessageData.HpMessage.MessageType.UDP).setChannelId(ctx.channel().id().asLongText()).build());
        messageBuilder.setData(ByteString.copyFrom(ByteBufUtil.getBytes(msg.content())));
        proxyHandler.getCtx().writeAndFlush(messageBuilder.build());
        final Attribute<InetSocketAddress> attr = ctx.channel().attr(SENDER);
        attr.set(msg.sender());
    }
}
