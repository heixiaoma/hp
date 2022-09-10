package net.hserver.hp.server;

import cn.hserver.HServerApplication;
import cn.hserver.core.ioc.annotation.HServerBoot;

/**
 * @author hxm
 */
@HServerBoot
public class StartServer {
    public static void main(String[] args) {
        HServerApplication.run(StartServer.class, 9090, args);
    }
}
