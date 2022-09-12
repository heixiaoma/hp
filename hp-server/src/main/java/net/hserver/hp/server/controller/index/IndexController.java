package net.hserver.hp.server.controller.index;

import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.interfaces.HttpResponse;
import net.hserver.hp.server.domian.entity.ProxyServerEntity;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    @GET("/")
    public void defaults(HttpResponse response) {
        response.sendTemplate("/index/default.ftl");
    }

    @GET("/index/index")
    public void index(HttpResponse response) {
        Map<String, Object> data = new HashMap<>();
        data.put("list", ProxyServerEntity.getAll());
        response.sendTemplate("/index/index.ftl", data);
    }


}
