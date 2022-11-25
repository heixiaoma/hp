package net.hserver.hp.proxy.config;

import cn.hserver.core.ioc.IocUtil;

public class CostConfig {
    public static String VER_TOKEN = "";
    private static final Integer M = 1024 * 1024;
    public static Integer LL = (int) (M * IocUtil.getBean(WebConfig.class).getLimit());
}
