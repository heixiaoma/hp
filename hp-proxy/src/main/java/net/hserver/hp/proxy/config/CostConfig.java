package net.hserver.hp.proxy.config;

import io.netty.channel.WriteBufferWaterMark;

public class CostConfig {
    public static String VER_TOKEN = "";
    public static final io.netty.channel.WriteBufferWaterMark WRITE_BUFFER_WATER_MARK = new WriteBufferWaterMark(10*1024 * 1024, 50 * 1024 * 1024);
}
