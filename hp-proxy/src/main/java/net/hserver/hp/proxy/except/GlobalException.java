package net.hserver.hp.proxy.except;

import cn.hserver.core.ioc.annotation.Bean;
import cn.hserver.plugin.web.context.Webkit;

@Bean
public class GlobalException implements cn.hserver.plugin.web.interfaces.GlobalException {
    @Override
    public void handler(Throwable throwable, int i, String s, Webkit webkit) {
        webkit.httpResponse.sendText(throwable.getMessage());
    }
}
