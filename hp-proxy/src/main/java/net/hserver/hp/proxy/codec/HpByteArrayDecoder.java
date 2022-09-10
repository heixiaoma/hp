package net.hserver.hp.proxy.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import net.hserver.hp.proxy.handler.TcpServer;

import java.util.List;

/**
 * 输出
 * @author hxm
 */
public class HpByteArrayDecoder extends ByteArrayDecoder {

    private TcpServer tcpServer;

    public HpByteArrayDecoder(TcpServer tcpServer) {
        this.tcpServer = tcpServer;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        tcpServer.addSend((long) msg.readableBytes());
        super.decode(ctx, msg, out);
    }
}
