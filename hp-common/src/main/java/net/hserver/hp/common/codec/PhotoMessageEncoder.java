package net.hserver.hp.common.codec;

import cn.hserver.core.server.util.PropUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import net.hserver.hp.common.handler.PhotoGifMessageHandler;
import net.hserver.hp.common.handler.PhotoJpgMessageHandler;
import net.hserver.hp.common.handler.PhotoMessageHandler;
import net.hserver.hp.common.handler.PhotoPngMessageHandler;

import java.util.ArrayList;
import java.util.List;

public class PhotoMessageEncoder extends ByteArrayEncoder {
    private final List<PhotoMessageHandler> photoMessageHandler = new ArrayList<>();

    private final static boolean isCheck= PropUtil.getInstance().getBoolean("photoCheck");
    public PhotoMessageEncoder(String username,String host) {
        photoMessageHandler.add(new PhotoJpgMessageHandler(username,host));
        photoMessageHandler.add(new PhotoPngMessageHandler(username,host));
        photoMessageHandler.add(new PhotoGifMessageHandler(username,host));
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, byte[] msg, List<Object> out) throws Exception {
        //校验缓存图片
        if (isCheck) {
            for (PhotoMessageHandler messageHandler : photoMessageHandler) {
                if (messageHandler.checkAndSavePhoto(msg)) {
                    break;
                }
            }
        }
        out.add(Unpooled.wrappedBuffer(msg));
    }
}
