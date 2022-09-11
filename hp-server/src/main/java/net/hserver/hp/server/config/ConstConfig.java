package net.hserver.hp.server.config;

import java.util.UUID;

public class ConstConfig {
    //注册模式：-1免费关闭注册 0 免费注册 >0 每天24小时时间内注册(小时数)
    public static int TIME = 0;
    public static String TIPS = "禁止穿透违法程序，免费不易 请大家谅解";
    public static String REG_TOKEN = UUID.randomUUID().toString();
}
