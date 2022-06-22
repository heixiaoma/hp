package net.hserver.hp.server;

import net.hserver.hp.server.config.WebConfig;
import cn.hserver.core.interfaces.FilterAdapter;
import cn.hserver.core.interfaces.HttpRequest;
import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.core.ioc.annotation.Bean;
import cn.hserver.core.server.context.ConstConfig;
import cn.hserver.core.server.context.Webkit;

import java.io.BufferedReader;
import java.io.FileReader;

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
        for (String s : URI) {
            if (request.getUri().contains(s)) {
                return;
            }
        }
        String auth = request.getHeader("cookie");
        String s = webConfig.getPassword();
        if (s != null && s.trim().length() > 0) {
            if (auth == null || !auth.contains("auth=" + s)) {
                webkit.httpResponse.sendTemplate("/login.ftl");
            }
        }
    }
}