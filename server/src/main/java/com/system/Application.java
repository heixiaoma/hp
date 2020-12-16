package com.system;

import top.hserver.HServerApplication;
import top.hserver.core.ioc.annotation.HServerBoot;

/**
 * @author hxm
 */
@HServerBoot
public class Application {
    public static void main(String[] args) {
        HServerApplication.run(Application.class, 8888, args);
    }
}
