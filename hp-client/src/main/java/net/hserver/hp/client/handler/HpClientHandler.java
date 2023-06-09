package net.hserver.hp.client.handler;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;
import net.hserver.hp.client.hp.CallMsg;
import net.hserver.hp.client.util.NamedThreadFactory;
import net.hserver.hp.common.exception.HpException;
import net.hserver.hp.common.protocol.HpMessageData;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author hxm
 */
@ChannelHandler.Sharable
public class HpClientHandler extends SimpleChannelInboundHandler<HpMessageData.HpMessage> {
    private final int port;
    private final String password;
    private final String username;
    private final String proxyAddress;
    private final String domain;
    private final int proxyPort;
    private final CallMsg callMsg;
    private final HpMessageData.HpMessage.MessageType type;
    private ChannelHandlerContext ctx;
    private final ConcurrentHashMap<String, LocalProxyHandler> TcpChannelHandlerMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, LocalProxyUdpClientHandler> UdpChannelHandlerMap = new ConcurrentHashMap<>();

    public HpClientHandler(HpMessageData.HpMessage.MessageType type, int port, String username, String password, String domain, String proxyAddress, int proxyPort, CallMsg callMsg) {
        this.port = port;
        this.type = type;
        this.password = password;
        this.username = username;
        this.domain = domain;
        this.proxyAddress = proxyAddress;
        this.proxyPort = proxyPort;
        this.callMsg = callMsg;
    }
    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        TcpChannelHandlerMap.values().forEach(targetChannel -> {
            targetChannel.getCtx().channel().config().setAutoRead(ctx.channel().isWritable());
        });
        UdpChannelHandlerMap.values().forEach(targetChannel -> {
            targetChannel.getCtx().channel().config().setAutoRead(ctx.channel().isWritable());
        });
        super.channelWritabilityChanged(ctx);
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        // register client information
        HpMessageData.HpMessage.Builder messageBuild = HpMessageData.HpMessage.newBuilder();
        messageBuild.setType(HpMessageData.HpMessage.HpMessageType.REGISTER);
        HpMessageData.HpMessage.MetaData.Builder metaDataBuild = HpMessageData.HpMessage.MetaData.newBuilder();
        metaDataBuild.setPort(port);
        metaDataBuild.setUsername(username);
        metaDataBuild.setPassword(password);
        metaDataBuild.setDomain(domain);
        metaDataBuild.setType(type);
        messageBuild.setMetaData(metaDataBuild.build());
        ctx.writeAndFlush(messageBuild.build());
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
            HpMessageData.HpMessage.Builder messageBuild = HpMessageData.HpMessage.newBuilder();
            messageBuild.setType(HpMessageData.HpMessage.HpMessageType.KEEPALIVE);
            ctx.writeAndFlush(messageBuild.build());
            System.out.println("发送心跳");
        } else {
            throw new HpException("Unknown type: " + message.getType());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        for (LocalProxyHandler value : TcpChannelHandlerMap.values()) {
            value.getCtx().close();
        }
        for (LocalProxyUdpClientHandler value : UdpChannelHandlerMap.values()) {
            value.getCtx().close();
        }
        TcpChannelHandlerMap.clear();
        UdpChannelHandlerMap.clear();
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
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(new NamedThreadFactory("HP-Local"));
        try {
            if (message.getMetaData().getType() == HpMessageData.HpMessage.MessageType.TCP) {
                LocalProxyHandler localProxyHandler = new LocalProxyHandler(this, message.getMetaData().getChannelId());
                Bootstrap b = new Bootstrap();
                b.group(workerGroup);
                b.channel(NioSocketChannel.class);
                b.option(ChannelOption.SO_KEEPALIVE, true);
                b.handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ByteArrayDecoder(), new ByteArrayEncoder(), localProxyHandler);
                        TcpChannelHandlerMap.put(message.getMetaData().getChannelId(), localProxyHandler);
                    }
                });
                Channel channel = b.connect(proxyAddress, proxyPort).sync().channel();
                channel.closeFuture().addListener(future -> workerGroup.shutdownGracefully());

            } else if (message.getMetaData().getType() == HpMessageData.HpMessage.MessageType.UDP) {
                LocalProxyUdpClientHandler localProxyUdpClientHandler = new LocalProxyUdpClientHandler(this, message.getMetaData().getChannelId());
                Bootstrap humClient = new Bootstrap();
                humClient.group(workerGroup)
                        .channel(NioDatagramChannel.class)
                        .option(ChannelOption.SO_BROADCAST, true)
                        .handler(new ChannelInitializer<Channel>() {
                            @Override
                            public void initChannel(Channel ch) throws Exception {
                                ch.pipeline().addLast(new ByteArrayDecoder(), new ByteArrayEncoder(), localProxyUdpClientHandler);
                                UdpChannelHandlerMap.put(message.getMetaData().getChannelId(), localProxyUdpClientHandler);
                            }
                        });
                Channel channel = humClient.bind(0).sync().channel();
                channel.closeFuture().addListener(future -> workerGroup.shutdownGracefully());
            }
        } catch (Exception e) {
            workerGroup.shutdownGracefully();
            HpMessageData.HpMessage.Builder messageBuild = HpMessageData.HpMessage.newBuilder();
            messageBuild.setType(HpMessageData.HpMessage.HpMessageType.DISCONNECTED);
            HpMessageData.HpMessage.MetaData.Builder metaDataBuild = HpMessageData.HpMessage.MetaData.newBuilder();
            metaDataBuild.setChannelId(message.getMetaData().getChannelId());
            messageBuild.setMetaData(metaDataBuild.build());
            ctx.writeAndFlush(messageBuild.build());
            TcpChannelHandlerMap.remove(message.getMetaData().getChannelId());
            UdpChannelHandlerMap.remove(message.getMetaData().getChannelId());
            callMsg.message(e.getMessage());
            throw e;
        }
    }

    /**
     * if HpMessage.getType() == HpMessageType.DISCONNECTED
     */
    private void processDisconnected(HpMessageData.HpMessage message) {
        String channelId = message.getMetaData().getChannelId();

        if (message.getMetaData().getType() == HpMessageData.HpMessage.MessageType.TCP) {
            LocalProxyHandler handler = TcpChannelHandlerMap.get(channelId);
            if (handler != null) {
                handler.getCtx().close();
                TcpChannelHandlerMap.remove(channelId);
            }
        }
        if (message.getMetaData().getType() == HpMessageData.HpMessage.MessageType.UDP) {
            LocalProxyUdpClientHandler handler = UdpChannelHandlerMap.get(channelId);
            if (handler != null) {
                handler.getCtx().close();
                UdpChannelHandlerMap.remove(channelId);
            }
        }
    }

    /**
     * if HpMessage.getType() == HpMessageType.DATA
     */
    private void processData(HpMessageData.HpMessage message) {
        String channelId = message.getMetaData().getChannelId();
        if (message.getMetaData().getType() == HpMessageData.HpMessage.MessageType.TCP) {
            LocalProxyHandler handler = TcpChannelHandlerMap.get(channelId);
            if (handler != null && handler.getCtx() != null) {
                ChannelHandlerContext ctx = handler.getCtx();
                ctx.writeAndFlush(message.getData().toByteArray());
            }
        }
        if (message.getMetaData().getType() == HpMessageData.HpMessage.MessageType.UDP) {
            LocalProxyUdpClientHandler handler = UdpChannelHandlerMap.get(channelId);
            if (handler != null && handler.getCtx() != null) {
                ChannelHandlerContext ctx = handler.getCtx();
                ctx.writeAndFlush(new DatagramPacket(Unpooled.wrappedBuffer(message.getData().toByteArray()), new InetSocketAddress(proxyAddress, proxyPort)));
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }
}
