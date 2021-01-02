package net.hserver.hp.common.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;
import net.hserver.hp.common.protocol.HpMessage;
import net.hserver.hp.common.protocol.HpMessageType;
import top.hserver.core.server.context.ConstConfig;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;


/**
 * @author hxm
 */
public class HpMessageEncoder extends MessageToByteEncoder<HpMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, HpMessage msg, ByteBuf out) throws Exception {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream)) {
            HpMessageType messageType = msg.getType();
            dataOutputStream.writeInt(messageType.getCode());
            String s = ConstConfig.JSON.writeValueAsString(msg.getMetaData());
            byte[] metaDataBytes = s.getBytes(CharsetUtil.UTF_8);
            dataOutputStream.writeInt(metaDataBytes.length);
            dataOutputStream.write(metaDataBytes);
            if (msg.getData() != null && msg.getData().length > 0) {
                dataOutputStream.write(msg.getData());
            }
            byte[] data = byteArrayOutputStream.toByteArray();
            out.writeInt(data.length);
            out.writeBytes(data);
        }

    }

}
