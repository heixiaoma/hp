package net.hserver.hp.server.controller;

import top.hserver.core.interfaces.HttpResponse;
import top.hserver.core.ioc.annotation.Controller;
import top.hserver.core.ioc.annotation.GET;

/**
 * @author hxm
 */
@Controller
public class IndexController {

    @GET("/")
    public void index(HttpResponse response){
        response.sendTemplate("/index.ftl");
    }

}
