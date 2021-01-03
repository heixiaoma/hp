package net.hserver.hp.server;

import top.hserver.HServerApplication;
import top.hserver.core.ioc.annotation.HServerBoot;

/**
 * @author hxm
 */
@HServerBoot
public class StartServer {
    public static void main(String[] args) {
        HServerApplication.run(StartServer.class, 9090, args);
    }
}
