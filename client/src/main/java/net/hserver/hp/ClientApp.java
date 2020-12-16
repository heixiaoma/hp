package net.hserver.hp;

import net.hserver.hp.client.BaseClient;
import net.hserver.hp.server.BaseServer;
import net.hserver.hp.server.ProxyServer;

/**
 * @author hxm
 */
public class ClientApp {

    public static void main(String[] args) throws Exception {
        new BaseServer().start();
        Thread.sleep(1000);
        new BaseClient().start();
        Thread.sleep(1000);
        new ProxyServer().start();
    }

}
