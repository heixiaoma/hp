package net.hserver.hp.server.controller.open;

import cn.hserver.core.queue.HServerQueue;
import cn.hserver.core.server.util.JsonResult;
import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.POST;

import java.util.Map;

@Controller
public class NoticeController {

    @POST("/notice/push")
    public JsonResult push(Map<String, String> data) {
        HServerQueue.sendQueue("EMAIL_PUSH", data.get("username"),data.get("title"), data.get("message"));
        return JsonResult.ok();
    }

}
