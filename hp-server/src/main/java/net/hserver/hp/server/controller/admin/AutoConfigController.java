package net.hserver.hp.server.controller.admin;

import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.interfaces.HttpResponse;
import net.hserver.hp.server.domian.entity.ConfigEntity;
import net.hserver.hp.server.service.ConfigService;
import org.beetl.sql.core.page.PageResult;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AutoConfigController {
    @Autowired
    private ConfigService configService;

    @GET("/admin/config")
    public void log(Integer page, HttpResponse response) {
        if (page == null) {
            page = 1;
        }
        PageResult<ConfigEntity> list = configService.list(page, 10);
        Map<String, Object> data = new HashMap<>(5);
        data.put("page", page);
        data.put("pageSize", 10);
        data.put("totalRow", list.getTotalRow());
        data.put("list", list.getList());
        data.put("totalPage", list.getTotalPage());
        response.sendTemplate("/admin/config.ftl", data);
    }


    @GET("/admin/config/remove")
    public void remove(Integer page, HttpResponse response, String id) {
        if (id != null) {
            configService.remove(id);
        }
        log(page, response);
    }
}
