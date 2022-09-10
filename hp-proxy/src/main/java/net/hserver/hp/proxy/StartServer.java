package net.hserver.hp.proxy;

import cn.hserver.HServerApplication;
import cn.hserver.core.ioc.annotation.HServerBoot;

/**
 * @author hxm
 */
@HServerBoot
public class StartServer {
    public static void main(String[] args) {
        HServerApplication.run(StartServer.class, new Integer[]{9091,80}, args);
    }
}
