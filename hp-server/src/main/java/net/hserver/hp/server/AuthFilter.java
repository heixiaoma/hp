package net.hserver.hp.server;

import top.hserver.core.interfaces.FilterAdapter;
import top.hserver.core.interfaces.HttpRequest;
import top.hserver.core.ioc.annotation.Bean;
import top.hserver.core.server.context.ConstConfig;
import top.hserver.core.server.context.Webkit;

import java.io.BufferedReader;
import java.io.FileReader;

@Bean
public class AuthFilter implements FilterAdapter {

    @Override
    public void doFilter(Webkit webkit) throws Exception {
        HttpRequest request = webkit.httpRequest;

        if (request.getUri().contains("login")||request.getUri().contains("reg")) {
            return;
        }

        String auth = request.getHeader("cookie");
        String s = readAuth();
        if (s != null) {
            if (auth == null || !auth.contains("auth=" + s)) {
                webkit.httpResponse.sendTemplate("/login.ftl");
            }
        }
    }

    public static String readAuth() {
        FileReader fileReader = null;
        BufferedReader in = null;
        try {
            fileReader = new FileReader(ConstConfig.PATH + "auth.txt");
            in = new BufferedReader(fileReader);
            String str;
            if ((str = in.readLine()) != null) {
                return str;
            }
        } catch (Exception ignored) {
        } finally {
            try {
                fileReader.close();
            } catch (Exception ignored) {
            }
            try {
                in.close();
            } catch (Exception ignored) {
            }
        }
        return null;
    }
}