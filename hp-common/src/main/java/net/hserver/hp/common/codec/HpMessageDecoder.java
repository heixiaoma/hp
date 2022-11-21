package net.hserver.hp.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.hserver.hp.common.protocol.HpMessageData;
import org.xerial.snappy.Snappy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * @author hxm
 */
public class HpMessageDecoder extends ByteToMessageDecoder {
    /**
     * <pre>
     * 协议开始的标准head_data，int类型，占据4个字节.
     * 表示是否解压isUncompress，byte类型，占据1个字节. 1标识要解压 否则不解压
     * 表示数据的长度contentLength，int类型，占据4个字节.
     * </pre>
     */
    public final int BASE_LENGTH = 4 + 1 + 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 可读长度必须大于基本长度
        if (in.readableBytes() >= BASE_LENGTH) {
            in.markReaderIndex();
            int header = in.readInt();
            if (header == 9999) {
                byte b = in.readByte();
                int dataLength = in.readInt();
                if (in.readableBytes() < dataLength) {
                    in.resetReaderIndex();
                } else {
                    byte[] data = new byte[dataLength];
                    in.readBytes(data);
                    if (b==1) {
                        //对数据进行zstd解压还原
                        byte[] uncompress = Snappy.uncompress(data);
                        out.add(HpMessageData.HpMessage.parseFrom(uncompress));
                    } else {
                        out.add(HpMessageData.HpMessage.parseFrom(data));
                    }
                }
            }
        }
    }
}
