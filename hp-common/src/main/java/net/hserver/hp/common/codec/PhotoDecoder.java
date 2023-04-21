package net.hserver.hp.common.codec;

import com.google.common.io.Files;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.util.internal.StringUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhotoDecoder {
    public static void print(String eventName, ByteBuf msg) {
        String chStr = "PNG";
        int length = msg.readableBytes();
        int outputLength = chStr.length() + 1 + eventName.length() + 2 + 10 + 1;
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        int hexDumpLength = 2 + rows * 80;
        outputLength += hexDumpLength;

        StringBuilder buf = new StringBuilder(outputLength);
        buf.append(chStr).append(' ').append(eventName).append(": ").append(length).append('B');
        buf.append(StringUtil.NEWLINE);
        ByteBufUtil.appendPrettyHexDump(buf, msg);
        System.out.println(buf.toString());
    }

    public static int isPngStart(byte[] bytes) {
        //89504E47 0D0A1A0A 0000000D 49484452
        List<Byte> hex = new ArrayList<Byte>() {
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

        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == hex.get(0) && i + hex.size() <= bytes.length) {
                int j = i;
                for (Byte aByte : hex) {
                    if (bytes[j] == aByte) {
                        j++;
                    } else {
                        break;
                    }
                }
                if (j == i + hex.size()) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static int isPngEnd(byte[] bytes) {
        //00004945 4E44AE42 6082
        List<Byte> hex = new ArrayList<Byte>() {
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

        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == hex.get(0) && i + hex.size() <= bytes.length) {
                int j = i;
                boolean flag = true;
                for (Byte aByte : hex) {
                    if (bytes[j] == aByte) {
                        j++;
                    } else {
                        flag=false;
                    }
                }
                if (flag) {
                    return j;
                }
            }
        }
        return -1;
    }


    public static void main(String[] args) throws IOException {
        File file = new File("/Users/heixiaoma/Documents/WechatIMG44.png");
        byte[] bytes = Files.toByteArray(file);
        print("ing", Unpooled.wrappedBuffer(bytes));
        System.out.println(isPngStart(bytes));
        System.out.println(isPngEnd(bytes));
        System.out.println(bytes.length);
    }


}
