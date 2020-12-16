package net.hserver.hp;
import net.hserver.hp.client.ProxyClient;
import net.hserver.hp.server.ProxyServer;

/**
 * @author hxm
 */
public class ClientApp {

    public static void main(String[] args) throws Exception{
        new ProxyServer().start();
        Thread.sleep(1000);
        new ProxyClient().start();
    }

}
