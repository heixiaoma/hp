package net.hserver.hp.server.controller.index;

import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.interfaces.HttpResponse;
import net.hserver.hp.server.domian.entity.ProxyServerEntity;
import net.hserver.hp.server.service.PayService;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private PayService payService;

    @GET("/")
    public void defaults(HttpResponse response) {
        Map<String, Object> data = new HashMap<>();
        data.put("payer",payService.getTop50());
        System.out.println(payService.getTop50());
        response.sendTemplate("/index/default.ftl",data);
    }

    @GET("/index/index")
    public void index(HttpResponse response) {
        Map<String, Object> data = new HashMap<>();
        data.put("list", ProxyServerEntity.getAll());
        response.sendTemplate("/index/index.ftl", data);
    }


}
