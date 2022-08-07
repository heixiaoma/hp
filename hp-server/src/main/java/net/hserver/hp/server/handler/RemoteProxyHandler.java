package net.hserver.hp.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import net.hserver.hp.common.handler.HpAbsHandler;
import net.hserver.hp.common.handler.HpCommonHandler;
import net.hserver.hp.common.protocol.HpMessage;
import net.hserver.hp.common.protocol.HpMessageType;

import java.util.HashMap;

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
        HpMessage message = new HpMessage();
        message.setType(HpMessageType.CONNECTED);
        HashMap<String, Object> metaData = new HashMap<>();
        metaData.put("channelId", ctx.channel().id().asLongText());
        message.setMetaData(metaData);
        proxyHandler.getCtx().writeAndFlush(message);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        HpMessage message = new HpMessage();
        message.setType(HpMessageType.DISCONNECTED);
        HashMap<String, Object> metaData = new HashMap<>();
        metaData.put("channelId", ctx.channel().id().asLongText());
        message.setMetaData(metaData);
        proxyHandler.getCtx().writeAndFlush(message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            byte[] data = (byte[]) msg;
            tcpServer.addPackNum();
            HpMessage message = new HpMessage();
            message.setType(HpMessageType.DATA);
            message.setData(data);
            HashMap<String, Object> metaData = new HashMap<>();
            metaData.put("channelId", ctx.channel().id().asLongText());
            message.setMetaData(metaData);
            proxyHandler.getCtx().writeAndFlush(message);
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
