package net.hserver.hp.proxy;

import cn.hserver.HServerApplication;
import cn.hserver.core.ioc.annotation.HServerBoot;
import cn.hserver.core.server.util.PropUtil;
import io.netty.util.ResourceLeakDetector;

/**
 * @author hxm
 */
@HServerBoot
public class StartProxy {
    public static void main(String[] args) {
        HServerApplication.run(StartProxy.class, new Integer[]{PropUtil.getInstance().getInt("port"),80}, args);
    }
}