package net.hserver.hp.common.handler;


import net.hserver.hp.common.message.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hxm
 */
public class PhotoPngMessageHandler extends PhotoMessageHandler{

    private final String username;
    private final String domain;

    public PhotoPngMessageHandler(String username, String domain) {
        this.username = username;
        this.domain = domain;
    }

    //89504E47 0D0A1A0A 0000000D 49484452
    private final static List<Byte> hexStart = new ArrayList<Byte>() {
        {
            add((byte) Integer.parseInt("89", 16));
            add((byte) Integer.parseInt("50", 16));
            add((byte) Integer.parseInt("4E", 16));
            add((byte) Integer.parseInt("47", 16));

            add((byte) Integer.parseInt("0D", 16));
            add((byte) Integer.parseInt("0A", 16));
            add((byte) Integer.parseInt("1A", 16));
            add((byte) Integer.parseInt("0A", 16));

            add((byte) Integer.parseInt("00", 16));
            add((byte) Integer.parseInt("00", 16));
            add((byte) Integer.parseInt("00", 16));
            add((byte) Integer.parseInt("0D", 16));

            add((byte) Integer.parseInt("49", 16));
            add((byte) Integer.parseInt("48", 16));
            add((byte) Integer.parseInt("44", 16));
            add((byte) Integer.parseInt("52", 16));
        }
    };


    //00004945 4E44AE42 6082
    private final static List<Byte> hexEnd = new ArrayList<Byte>() {
        {
            add((byte) Integer.parseInt("49", 16));
            add((byte) Integer.parseInt("45", 16));

            add((byte) Integer.parseInt("4E", 16));
            add((byte) Integer.parseInt("44", 16));
            add((byte) Integer.parseInt("AE", 16));
            add((byte) Integer.parseInt("42", 16));

            add((byte) Integer.parseInt("60", 16));
            add((byte) Integer.parseInt("82", 16));
        }
    };


    public static int isPngStart(byte[] bytes) {
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

    public static int isPngEnd(byte[] bytes) {
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
            int pngStart = isPngStart(bytes);
            int pngEnd = isPngEnd(bytes);
            //一个数据包搞定的情况
            if (pngEnd >= 0 && pngStart >= 0) {
                byte[] data = new byte[pngEnd - pngStart];
                System.arraycopy(bytes, pngStart, data, 0, data.length);
                add(data);
                save(new Photo(username,domain,Photo.PhotoType.PNG, change()));
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
                    save(new Photo(username,domain,Photo.PhotoType.PNG, change()));
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
