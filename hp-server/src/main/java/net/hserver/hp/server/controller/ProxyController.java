package net.hserver.hp.server.controller;

import cn.hserver.core.server.util.JsonResult;
import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.annotation.POST;
import cn.hserver.plugin.web.interfaces.HttpResponse;
import net.hserver.hp.server.domian.bean.GlobalStat;
import net.hserver.hp.server.domian.entity.ProxyServerEntity;

import java.util.HashMap;

@Controller
public class ProxyController {

    @GET("/admin/proxy")
    public void tips(HttpResponse response) {
        HashMap<String, Object> tips = new HashMap<>();
        tips.put("list", ProxyServerEntity.getAll());
        response.sendTemplate("/proxy.ftl", tips);
    }
    @POST("/open/proxy/reg")
    public JsonResult regServer(ProxyServerEntity proxyServerEntity) {
        ProxyServerEntity.add(proxyServerEntity);
        return JsonResult.ok();
    }

}
