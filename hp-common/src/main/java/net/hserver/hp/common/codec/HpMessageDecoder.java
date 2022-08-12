package net.hserver.hp.common.codec;

import com.google.protobuf.MessageLite;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import net.hserver.hp.common.protocol.HpMessageData;

import java.util.List;

/**
 * @author hxm
 */
public class HpMessageDecoder extends ProtobufDecoder {


    public HpMessageDecoder() {
        super(HpMessageData.HpMessage.getDefaultInstance());
    }
}
