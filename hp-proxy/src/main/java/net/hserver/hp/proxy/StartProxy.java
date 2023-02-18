package net.hserver.hp.proxy;

import cn.hserver.HServerApplication;
import cn.hserver.core.ioc.annotation.HServerBoot;
import cn.hserver.core.server.util.PropUtil;
import io.netty.channel.ChannelOption;
import io.netty.channel.WriteBufferWaterMark;

import static net.hserver.hp.proxy.config.CostConfig.WRITE_BUFFER_WATER_MARK;

/**
 * @author hxm
 */
@HServerBoot
public class StartProxy {
    public static void main(String[] args) {
        HServerApplication.addTcpOptions(ChannelOption.WRITE_BUFFER_WATER_MARK,WRITE_BUFFER_WATER_MARK);
        HServerApplication.run(StartProxy.class, new Integer[]{PropUtil.getInstance().getInt("port"), 80, 443}, args);
    }
}