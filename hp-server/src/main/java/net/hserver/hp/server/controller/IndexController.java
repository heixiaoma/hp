package net.hserver.hp.server.controller;

import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.interfaces.HttpResponse;

@Controller
public class IndexController {

    @GET("/")
    public void index(HttpResponse response){
        response.sendTemplate("/index/index.ftl");
    }

}
