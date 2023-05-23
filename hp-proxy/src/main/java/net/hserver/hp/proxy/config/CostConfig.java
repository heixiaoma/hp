package net.hserver.hp.proxy.config;


import cn.hserver.core.server.util.PropUtil;
import net.hserver.hp.proxy.domian.bean.ConInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CostConfig {

    public final static Map<String, ConInfo> IP_USER = new ConcurrentHashMap<>();

}
