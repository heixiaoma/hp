package net.hserver.hp.proxy;

import cn.hserver.HServerApplication;
import cn.hserver.core.ioc.annotation.HServerBoot;
import cn.hserver.core.server.util.PropUtil;
import io.netty.channel.ChannelOption;
import io.netty.channel.WriteBufferWaterMark;

/**
 * @author hxm
 */
@HServerBoot
public class StartProxy {
    public static void main(String[] args) {
        HServerApplication.addTcpOptions(ChannelOption.valueOf("WRITE_BUFFER_WATER_MARK"), new WriteBufferWaterMark(1024 * 1024 * 5, 1024 * 1024 * 10));
        HServerApplication.run(StartProxy.class, new Integer[]{PropUtil.getInstance().getInt("port"), 80, 443}, args);
    }
}