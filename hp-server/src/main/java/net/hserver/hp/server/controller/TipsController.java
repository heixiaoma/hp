package net.hserver.hp.server.controller;

import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.annotation.POST;
import cn.hserver.plugin.web.interfaces.HttpResponse;
import net.hserver.hp.server.handler.HpServerHandler;

import java.util.HashMap;

@Controller
public class TipsController {

    @GET("/admin/tips")
    public void tips(HttpResponse response) {
        HashMap<String, Object> tips = new HashMap<>();
        tips.put("tips", HpServerHandler.tips);
        response.sendTemplate("/tips.ftl", tips);
    }

    @POST("/admin/setTips")
    public void setTips(String tips, HttpResponse response) {
        if (tips != null) {
            HpServerHandler.tips = tips.trim();
        }
        tips(response);
    }
}
