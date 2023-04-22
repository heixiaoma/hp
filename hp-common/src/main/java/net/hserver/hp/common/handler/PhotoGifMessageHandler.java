package net.hserver.hp.common.handler;

import cn.hserver.core.server.context.ConstConfig;
import com.google.common.io.Files;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import net.hserver.hp.common.message.Photo;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author hxm
 */
public class PhotoGifMessageHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private String host;

    public PhotoGifMessageHandler(String host) {
        this.host = host;
    }

    private final static List<Byte> hexStart = new ArrayList<Byte>() {
        {
            add((byte) Integer.parseInt("47", 16));
            add((byte) Integer.parseInt("49", 16));
            add((byte) Integer.parseInt("46", 16));
        }
    };


    //00004945 4E44AE42 6082
    private final static List<Byte> hexEnd = new ArrayList<Byte>() {
        {
            add((byte) Integer.parseInt("00", 16));
            add((byte) Integer.parseInt("3B", 16));
        }
    };


    public static int isGifStart(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == hexStart.get(0) && i + hexStart.size() <= bytes.length) {
                int j = i;
                for (Byte aByte : hexStart) {
                    if (bytes[j] == aByte) {
                        j++;
                    } else {
                        break;
                    }
                }
                if (j == i + hexStart.size()) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static int isGifEnd(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == hexEnd.get(0) && i + hexEnd.size() <= bytes.length) {
                int j = i;
                boolean flag = true;
                for (Byte aByte : hexEnd) {
                    if (bytes[j] == aByte) {
                        j++;
                    } else {
                        flag = false;
                    }
                }
                if (flag) {
                    return j;
                }
            }
        }
        return -1;
    }

    public byte[] change() {
        byte[] b = new byte[photo.size()];
        for (int i = 0; i < photo.size(); i++) {
            b[i] = photo.get(i);
        }
        photo.clear();
        return b;
    }

    public void add(byte[] bytes) {
        for (byte aByte : bytes) {
            photo.add(aByte);
        }
    }

    private final List<Byte> photo = new ArrayList<>();

    private boolean flag = false;


    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        try {
            ReferenceCountUtil.retain(in);
            byte[] bytes = ByteBufUtil.getBytes(in);
            int pngStart = isGifStart(bytes);
            int pngEnd = isGifEnd(bytes);
            //一个数据包搞定的情况
            if (pngEnd > 0 && pngStart > 0) {
                byte[] data = new byte[pngEnd - pngStart];
                System.arraycopy(bytes, pngStart, data, 0, data.length);
                add(data);
                save(new Photo(Photo.PhotoType.GIF, change()));
            } else {
                if (pngStart > 0 && pngEnd == -1) {
                    in.markReaderIndex();
                    flag = true;
                    byte[] data = new byte[in.readableBytes() - pngStart];
                    System.arraycopy(bytes, pngStart, data, 0, data.length);
                    add(data);
                } else if (flag && pngStart == -1 && pngEnd == -1) {
                    add(bytes);
                    //大图就直接清空最大10MB的图片
                    if (photo.size()>1000*1024*10){
                        photo.clear();
                        flag=false;
                    }
                } else if (flag && pngStart == -1 && pngEnd > 0) {
                    byte[] data = new byte[pngEnd];
                    System.arraycopy(bytes, 0, data, 0, data.length);
                    add(data);
                    flag = false;
                    save(new Photo(Photo.PhotoType.GIF, change()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ctx.fireChannelRead(in);
    }

    public void save(Photo photo) {
        try {
            String path = ConstConfig.PATH + "photo" + File.separator + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + File.separator;
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            Files.write(photo.getData(), new File(path +host+ UUID.randomUUID() + photo.getPhotoType().getTips()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
