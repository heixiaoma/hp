package net.hserver.hp.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.hserver.hp.common.utils.SerializationUtil;

/**
 * @author hxm
 */
public class HpMessageEncoder extends MessageToByteEncoder {

    private final Class<?> genericClass;

    public HpMessageEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) {
        if (genericClass.isInstance(in)) {
            byte[] data = SerializationUtil.serialize(in);
            //header HP 72,80
            out.writeInt(72);
            out.writeInt(80);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }

}
