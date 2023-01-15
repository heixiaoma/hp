package net.hserver.hp.proxy.handler;

import io.netty.handler.traffic.GlobalTrafficShapingHandler;
import io.netty.util.concurrent.EventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.hserver.hp.proxy.config.CostConfig.LIMIT;


public class FlowGlobalTrafficShapingHandler extends GlobalTrafficShapingHandler {
    private static final Logger log = LoggerFactory.getLogger(FlowGlobalTrafficShapingHandler.class);
    /**
     * 默认100kb 更具宽带动态调整
     */
    public FlowGlobalTrafficShapingHandler(EventExecutor executor) {
        super(executor,LIMIT,LIMIT);
    }
}
