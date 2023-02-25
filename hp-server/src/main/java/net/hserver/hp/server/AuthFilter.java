package net.hserver.hp.server;

import cn.hserver.plugin.web.context.Webkit;
import cn.hserver.plugin.web.interfaces.FilterAdapter;
import cn.hserver.plugin.web.interfaces.HttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import net.hserver.hp.server.config.WebConfig;
import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.core.ioc.annotation.Bean;
import net.hserver.hp.server.domian.entity.UserEntity;
import net.hserver.hp.server.service.UserService;

/**
 * @author hxm
 */
@Bean
public class AuthFilter implements FilterAdapter {

    @Autowired
    private WebConfig webConfig;

    @Autowired
    private UserService userService;

    @Override
    public void doFilter(Webkit webkit) throws Exception {
        webkit.httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        webkit.httpResponse.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        webkit.httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        webkit.httpResponse.setHeader("Access-Control-Allow-Headers", "*");
        if (webkit.httpRequest.getRequestType().equals(HttpMethod.OPTIONS)) {
            webkit.httpResponse.sendHtml("");
            return;
        }
        HttpRequest request = webkit.httpRequest;
        if (request.getUri().contains("admin")) {
            String auth = request.getHeader("cookie");
            String s = webConfig.getPassword();
            if (s != null && s.trim().length() > 0) {
                if (auth == null || !auth.contains("auth=" + s)) {
                    webkit.httpResponse.sendTemplate("/admin/login.ftl");
                }
            }
        } else if (request.getUri().contains("index")) {
            String auth = request.getHeader("cookie");
            try {
                if (auth != null) {
                    String[] split = auth.split(";");
                    for (String s : split) {
                        boolean contains = s.contains("authUser=");
                        if (contains) {
                            String s1 = s.replaceAll("authUser=", "");
                            String[] split1 = s1.split("\\|");
                            String user = split1[0];
                            String pwd = split1[1];
                            UserEntity user1 = userService.getUser(user);
                            if (user1 != null && user1.getPassword().equals(pwd)) {
                                webkit.httpRequest.getHeaders().put("username", user);
                                return;
                            }
                        }
                    }

                }
            } catch (Exception ignored) {
            }
            webkit.httpResponse.sendTemplate("/index/default.ftl");
        }
    }
}
