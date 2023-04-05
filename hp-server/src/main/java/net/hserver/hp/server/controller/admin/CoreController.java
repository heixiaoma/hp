package net.hserver.hp.server.controller.admin;

import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.annotation.POST;
import cn.hserver.plugin.web.context.PartFile;
import cn.hserver.plugin.web.interfaces.HttpRequest;
import cn.hserver.plugin.web.interfaces.HttpResponse;
import net.hserver.hp.server.domian.entity.AppEntity;
import net.hserver.hp.server.domian.entity.CoreEntity;
import net.hserver.hp.server.service.AppService;
import net.hserver.hp.server.service.CoreService;
import org.beetl.sql.core.page.PageResult;
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
public class CoreController {
    private static final Logger log = LoggerFactory.getLogger(CoreController.class);

    @Autowired
    private CoreService coreService;

    @GET("/admin/core")
    public void index(Integer page, HttpResponse response) {
        if (page == null) {
            page = 1;
        }
        PageResult<CoreEntity> list = coreService.list(page, 10);
        Map<String, Object> data = new HashMap<>(5);
        data.put("page", page);
        data.put("pageSize", 10);
        data.put("totalRow", list.getTotalRow());
        data.put("list", list.getList());
        data.put("totalPage", list.getTotalPage());
        response.sendTemplate("/admin/core.ftl", data);
    }

    @POST("/admin/core/add")
    public void add(Integer page, HttpResponse response, CoreEntity coreEntity) {
        try {
            if (coreEntity != null && coreEntity.getVersionCode().trim().length() > 0 && coreEntity.getUpdateContent().trim().length() > 0) {
                coreService.add(coreEntity);
            }
        } catch (Exception ignored) {
        }
        index(page, response);
    }

    @GET("/admin/core/remove")
    public void remove(Integer page, HttpResponse response, String id) {
        if (id != null) {
            coreService.remove(id);
        }
        index(page, response);
    }
}
