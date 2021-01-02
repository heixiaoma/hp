package net.hserver.hp.server.handler;

import io.netty.channel.ChannelHandlerContext;
import net.hserver.hp.common.handler.HpCommonHandler;
import net.hserver.hp.common.protocol.HpMessage;
import net.hserver.hp.common.protocol.HpMessageType;

import java.util.HashMap;

/**
 * @author hxm
 */
public class RemoteProxyHandler extends HpCommonHandler {

    private HpCommonHandler proxyHandler;

    public RemoteProxyHandler(HpCommonHandler proxyHandler) {
        this.proxyHandler = proxyHandler;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
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
        byte[] data = (byte[]) msg;
        HpMessage message = new HpMessage();
        message.setType(HpMessageType.DATA);
        message.setData(data);
        HashMap<String, Object> metaData = new HashMap<>();
        metaData.put("channelId", ctx.channel().id().asLongText());
        message.setMetaData(metaData);
        proxyHandler.getCtx().writeAndFlush(message);
    }
}
