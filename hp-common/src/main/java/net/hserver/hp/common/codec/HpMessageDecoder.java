package net.hserver.hp.common.codec;

import com.fasterxml.jackson.core.type.TypeReference;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;
import net.hserver.hp.common.protocol.HpMessage;
import net.hserver.hp.common.protocol.HpMessageType;
import top.hserver.core.server.context.ConstConfig;

import java.util.List;
import java.util.Map;

/**
 * @author hxm
 */
public class HpMessageDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List out) throws Exception {

        int type = msg.readInt();
        HpMessageType messageType = HpMessageType.valueOf(type);

        int metaDataLength = msg.readInt();
        CharSequence metaDataString = msg.readCharSequence(metaDataLength, CharsetUtil.UTF_8);

        String s = metaDataString.toString();
        Map<String, Object> metaData = ConstConfig.JSON.readValue(s, new TypeReference<Map<String, Object>>() {
        });
        byte[] data = null;
        if (msg.isReadable()) {
            data = ByteBufUtil.getBytes(msg);
        }

        HpMessage message = new HpMessage();
        message.setType(messageType);
        message.setMetaData(metaData);
        message.setData(data);

        out.add(message);
    }

}
