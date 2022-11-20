package net.hserver.hp.proxy.handler.proxy;

import com.google.protobuf.ByteString;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import net.hserver.hp.common.handler.HpCommonHandler;
import net.hserver.hp.common.protocol.HpMessageData;
import net.hserver.hp.proxy.handler.TcpServer;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RemoteUdpServerHandler extends
        SimpleChannelInboundHandler<DatagramPacket> {

    private final TcpServer tcpServer;

    private final HpCommonHandler proxyHandler;

    private ChannelHandlerContext channelHandlerContext;

    private final Map<String, InetSocketAddress> Sender = new ConcurrentHashMap<>();

    public InetSocketAddress getSender(String id) {
        InetSocketAddress inetSocketAddress = Sender.get(id);
        System.out.println(inetSocketAddress);
        return inetSocketAddress;
    }

    public RemoteUdpServerHandler(HpCommonHandler proxyHandler, TcpServer tcpServer) {
        this.tcpServer = tcpServer;
        this.proxyHandler = proxyHandler;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Sender.remove(ctx.channel().id().asLongText());
        System.out.println("删除发送者");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
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
        this.channelHandlerContext = ctx;
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
        Sender.remove(ctx.channel().id().asLongText());
        Sender.put(ctx.channel().id().asLongText(), msg.sender());
        System.out.println(ctx.channel().id().asLongText() + msg.sender());
        System.out.println("外网的UDP数据返回到内网去 由内网客服端去转发");
    }
}
