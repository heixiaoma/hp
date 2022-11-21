package net.hserver.hp.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import net.hserver.hp.common.protocol.HpMessageData;
import net.hserver.hp.common.utils.SerializationUtil;
import net.jpountz.lz4.LZ4Factory;
import org.xerial.snappy.Snappy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.atomic.LongAdder;
import java.util.zip.GZIPOutputStream;

/**
 * @author hxm
 */
public class HpMessageEncoder extends MessageToByteEncoder<HpMessageData.HpMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, HpMessageData.HpMessage msg, ByteBuf out) throws Exception {
        // 1.写入消息的开头的信息标志(int类型)
        out.writeInt(9999);
        byte[] bytes = msg.toByteArray();
        //数据压缩
        byte[] compress = Snappy.compress(bytes);
        // 2.写入消息的长度(int 类型)
        if (compress.length<bytes.length) {
            out.writeByte(1);
            out.writeInt(compress.length);
            out.writeBytes(compress);
        } else {
            out.writeByte(-1);
            out.writeInt(bytes.length);
            // 3.写入消息的内容(byte[]类型)
            out.writeBytes(bytes);
        }
    }
}
