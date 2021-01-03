package net.hserver.hp.server.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.hserver.core.interfaces.InitRunner;
import top.hserver.core.ioc.annotation.Bean;

/**
 * @author hxm
 */
@Bean
public class ServerInit implements InitRunner {

    private static final Logger logger = LoggerFactory.getLogger(ServerInit.class);

    @Override
    public void init(String[] strings) {
        int port = 7731;
        HpServer server = new HpServer();
        try {
            server.start(port);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("HP Server 启动成功 port " + port);
    }
}