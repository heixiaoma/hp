package net.hserver.hp.server.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import net.hserver.hp.server.handler.TcpServer;

import java.util.List;

/**
 * 输入
 * @author hxm
 */
public class HpByteArrayEncoder extends ByteArrayEncoder {

    private TcpServer tcpServer;

    public HpByteArrayEncoder(TcpServer tcpServer) {
        this.tcpServer = tcpServer;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, byte[] msg, List<Object> out) throws Exception {
        ByteBuf byteBuf = Unpooled.wrappedBuffer(msg);
        tcpServer.addReceive((long) byteBuf.readableBytes());
        out.add(Unpooled.wrappedBuffer(msg));
    }
}
