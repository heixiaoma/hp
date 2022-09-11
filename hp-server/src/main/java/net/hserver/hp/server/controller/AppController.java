package net.hserver.hp.server.controller;

import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.annotation.POST;
import cn.hserver.plugin.web.context.PartFile;
import cn.hserver.plugin.web.interfaces.HttpRequest;
import cn.hserver.plugin.web.interfaces.HttpResponse;
import cn.hserver.plugin.web.interfaces.ProgressStatus;
import net.hserver.hp.server.domian.entity.AppEntity;
import net.hserver.hp.server.service.AppService;
import org.beetl.sql.core.page.PageResult;
import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.core.server.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hxm
 */
@Controller
public class AppController {
    private static final Logger log = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private AppService appService;

    @GET("/admin/app")
    public void index(Integer page, HttpResponse response) {
        if (page == null) {
            page = 1;
        }
        PageResult<AppEntity> list = appService.list(page, 10);
        Map<String, Object> data = new HashMap<>(5);
        data.put("page", page);
        data.put("pageSize", 10);
        data.put("totalRow", list.getTotalRow());
        data.put("list", list.getList());
        data.put("totalPage", list.getTotalPage());
        response.sendTemplate("/app.ftl", data);
    }

    @POST("/admin/app/add")
    public void add(Integer page, HttpResponse response, AppEntity appEntity) {
        try {
            if (appEntity != null && appEntity.getVersionCode().trim().length() > 0 && appEntity.getUpdateContent().trim().length() > 0) {
                appService.add(appEntity);
            }
        } catch (Exception ignored) {
        }
        index(page, response);
    }

    @POST("/admin/app/upload")
    public void add(Integer page, HttpResponse response, HttpRequest request) throws IOException {
        PartFile apk = request.queryFile("apk");
        apk.moveTo(new File("./hp-client.apk"));
        index(page, response);
    }

    @GET("/admin/app/remove")
    public void remove(Integer page, HttpResponse response, String id) {
        if (id != null) {
            appService.remove(id);
        }
        index(page, response);
    }
}
