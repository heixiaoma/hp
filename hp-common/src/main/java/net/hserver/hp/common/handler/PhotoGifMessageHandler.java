package net.hserver.hp.common.handler;

import net.hserver.hp.common.message.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hxm
 */
public class PhotoGifMessageHandler extends PhotoMessageHandler {

    private final String username;
    private final String domain;

    public PhotoGifMessageHandler(String username, String domain) {
        this.username = username;
        this.domain = domain;
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
    public boolean checkAndSavePhoto(byte[] bytes) {
        try {
            int pngStart = isGifStart(bytes);
            int pngEnd = isGifEnd(bytes);
            //一个数据包搞定的情况
            if (pngEnd >= 0 && pngStart >= 0) {
                byte[] data = new byte[pngEnd - pngStart];
                System.arraycopy(bytes, pngStart, data, 0, data.length);
                add(data);
                save(new Photo(username,domain,Photo.PhotoType.GIF, change()));
            } else {
                if (pngStart >= 0 && pngEnd == -1) {
                    flag = true;
                    byte[] data = new byte[bytes.length - pngStart];
                    System.arraycopy(bytes, pngStart, data, 0, data.length);
                    add(data);
                } else if (flag && pngStart == -1 && pngEnd == -1) {
                    add(bytes);
                    //大图就直接清空最大10MB的图片
                    if (photo.size()>1000*1024*10){
                        photo.clear();
                        flag=false;
                    }
                } else if (flag && pngStart == -1 && pngEnd >= 0) {
                    byte[] data = new byte[pngEnd];
                    System.arraycopy(bytes, 0, data, 0, data.length);
                    add(data);
                    flag = false;
                    save(new Photo(username,domain,Photo.PhotoType.GIF, change()));
                }else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
