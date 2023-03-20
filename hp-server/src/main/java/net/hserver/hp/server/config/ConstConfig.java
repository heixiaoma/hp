package net.hserver.hp.server.config;

import cn.hserver.core.server.util.PropUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ConstConfig {
    //注册模式：-1免费关闭注册 0 免费注册 >0 每天24小时时间内注册(小时数)
    public static int TIME = 0;
    public static int PROXY_SIZE = Integer.parseInt(PropUtil.getInstance().get("proxy.size","3"));
    public static String TIPS = "禁止穿透违法程序，免费不易 请大家谅解";
    public static String REG_TOKEN = UUID.randomUUID().toString();

    /**
     * 邮箱验证码
     */
    public static final Cache<String, String> EMAIL_CODE = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).build();

    public static final Cache<String, String> EMAIL = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();

}
