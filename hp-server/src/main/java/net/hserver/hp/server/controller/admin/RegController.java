package net.hserver.hp.server.controller.admin;

import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.annotation.POST;
import cn.hserver.plugin.web.interfaces.HttpResponse;
import net.hserver.hp.server.config.ConstConfig;

import java.util.HashMap;

@Controller
public class RegController {

    @GET("/admin/reg")
    public void reg(HttpResponse response) {
        HashMap<String, Object> reg = new HashMap<>();
        reg.put("time", ConstConfig.TIME);
        response.sendTemplate("/admin/reg.ftl", reg);
    }

    @POST("/admin/setTime")
    public void setTime(Integer time, HttpResponse response) {
        if (time != null) {
            ConstConfig.TIME = time;
        }
        reg(response);
    }
}
