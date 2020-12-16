package com.system.filter;

import io.netty.handler.codec.http.HttpMethod;
import top.hserver.core.interfaces.FilterAdapter;
import top.hserver.core.ioc.annotation.Bean;
import top.hserver.core.server.context.Webkit;

@Bean
public class ReqFilter implements FilterAdapter {

    @Override
    public void doFilter(Webkit webkit) throws Exception {

        webkit.httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        webkit.httpResponse.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE"); // 允许请求的类型
        webkit.httpResponse.setHeader("Access-Control-Allow-Credentials", " true"); // 设置是否允许发送 cookies
        webkit.httpResponse.setHeader("Access-Control-Allow-Headers", " Content-Type,Content-Length,Accept-Encoding,Accept,X-Requested-with, Origin,Access-Token,X-Access-Token,x-access-token"); // 设置允许自定义请求头的字段'

        if (webkit.httpRequest.getRequestType() == HttpMethod.OPTIONS) {
            webkit.httpResponse.sendHtml("");
        }
    }
}
