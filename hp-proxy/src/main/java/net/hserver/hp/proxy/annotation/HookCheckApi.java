package net.hserver.hp.proxy.annotation;

import cn.hserver.core.interfaces.HookAdapter;
import cn.hserver.core.ioc.annotation.Hook;
import cn.hserver.plugin.web.context.HServerContextHolder;
import cn.hserver.plugin.web.interfaces.HttpRequest;
import cn.hserver.plugin.web.interfaces.HttpResponse;
import net.hserver.hp.proxy.config.CostConfig;

import java.lang.reflect.Method;

@Hook(CheckApi.class)
public class HookCheckApi implements HookAdapter {

    @Override
    public void before(Class aClass, Method method, Object[] objects) throws Throwable {
        HttpRequest httpRequest = HServerContextHolder.getWebKit().httpRequest;
        String token = httpRequest.query("token");
        System.out.println(token);
        if (token == null || !token.equals(CostConfig.VER_TOKEN)) {
            throw new Exception("token 校验失败");
        }
    }

    @Override
    public Object after(Class aClass, Method method, Object o) {
        return o;
    }

    @Override
    public void throwable(Class aClass, Method method, Throwable throwable) {

    }
}
