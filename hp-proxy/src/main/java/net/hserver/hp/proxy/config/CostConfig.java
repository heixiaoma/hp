package net.hserver.hp.proxy.config;

import cn.hserver.core.ioc.IocUtil;

public class CostConfig {
    public static String VER_TOKEN = "";
    private static final Integer M = 1024 * 1024;
    public static Integer LH = M * 50;
    //默认5M
    public static Integer LL = M * IocUtil.getBean(WebConfig.class).getLimit();
}
