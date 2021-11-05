package net.hserver.hp.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.hserver.hp.common.utils.SerializationUtil;

import java.util.List;

/**
 * @author hxm
 */
public class HpMessageDecoder extends ByteToMessageDecoder {

    private Class<?> genericClass;

    public HpMessageDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        //因为之前编码的时候写入4个Int型，4个字节来表示长度
        if (in.readableBytes() < 4*4) {
            return;
        }
        in.markReaderIndex();
        int r = in.readInt();
        int p = in.readInt();
        if (r != 'H' && p != 'P') {
            return;
        }
        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        out.add(SerializationUtil.deserialize(data, genericClass));
    }

}
