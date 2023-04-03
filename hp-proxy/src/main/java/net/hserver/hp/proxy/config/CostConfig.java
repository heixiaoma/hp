package net.hserver.hp.proxy.config;


import cn.hserver.core.server.util.PropUtil;
import net.hserver.hp.proxy.domian.bean.ConInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CostConfig {
    //集群分配值
    public static String VER_TOKEN = "";

    public static final Integer LONGIN_ERROR= Integer.parseInt(PropUtil.getInstance().get("login-error","100"));

    public final static Map<String, ConInfo> IP_USER = new ConcurrentHashMap<>();

}
