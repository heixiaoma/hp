package net.hserver.hp.common.codec;

import cn.hserver.core.server.util.PropUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import net.hserver.hp.common.handler.PhotoGifMessageHandler;
import net.hserver.hp.common.handler.PhotoJpgMessageHandler;
import net.hserver.hp.common.handler.PhotoMessageHandler;
import net.hserver.hp.common.handler.PhotoPngMessageHandler;

import java.util.ArrayList;
import java.util.List;

public class PhotoMessageDecoder extends ByteArrayDecoder {

    private final List<PhotoMessageHandler> photoMessageHandler = new ArrayList<>();
    private final static boolean isCheck= PropUtil.getInstance().getBoolean("photoCheck");

    public PhotoMessageDecoder(String username,String host) {
        photoMessageHandler.add(new PhotoJpgMessageHandler(username,host));
        photoMessageHandler.add(new PhotoPngMessageHandler(username,host));
        photoMessageHandler.add(new PhotoGifMessageHandler(username,host));
    }
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        byte[] bytes = ByteBufUtil.getBytes(msg);
        if (isCheck) {
            //校验缓存图片
            for (PhotoMessageHandler messageHandler : photoMessageHandler) {
                if (messageHandler.checkAndSavePhoto(bytes)) {
                    break;
                }
            }
        }
        out.add(bytes);
    }
}
