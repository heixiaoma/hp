package net.hserver.hp.server.exception;

import cn.hserver.HServerApplication;
import cn.hserver.core.ioc.annotation.Bean;
import cn.hserver.plugin.web.context.Webkit;
import cn.hserver.plugin.web.exception.NotFoundException;
import cn.hserver.plugin.web.interfaces.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Bean

public class Ex implements GlobalException {
    private static final Logger log = LoggerFactory.getLogger(HServerApplication.class);

    @Override
    public void handler(Throwable throwable, int i, String s, Webkit webkit) {
        if (throwable instanceof NotFoundException) {
            webkit.httpResponse.sendText("未知地址:" + webkit.httpRequest.getIpAddress());
        }else {
            log.error("异常：{},Ip:{},{}", throwable.getMessage(), webkit.httpRequest.getIpAddress(), webkit.httpRequest.getNettyUri());
            webkit.httpResponse.sendText("未知地址");
        }
    }
}
