package net.hserver.hp.server;

import cn.hserver.plugin.web.context.Webkit;
import cn.hserver.plugin.web.interfaces.FilterAdapter;
import cn.hserver.plugin.web.interfaces.HttpRequest;
import net.hserver.hp.server.config.WebConfig;
import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.core.ioc.annotation.Bean;

/**
 * @author hxm
 */
@Bean
public class AuthFilter implements FilterAdapter {

    @Autowired
    private WebConfig webConfig;

    private static final String[] URI = {"login", "reg", "getVersion", "download", "getMyInfo"};

    @Override
    public void doFilter(Webkit webkit) throws Exception {
        HttpRequest request = webkit.httpRequest;
        if (request.getUri().contains("admin")) {
            String auth = request.getHeader("cookie");
            String s = webConfig.getPassword();
            if (s != null && s.trim().length() > 0) {
                if (auth == null || !auth.contains("auth=" + s)) {
                    webkit.httpResponse.sendTemplate("/login.ftl");
                }
            }
        }
    }
}