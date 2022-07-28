package net.hserver.hp.server.controller;

import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.interfaces.HttpResponse;
import net.hserver.hp.server.domian.entity.AppEntity;
import net.hserver.hp.server.domian.entity.StatisticsEntity;
import net.hserver.hp.server.handler.HpServerHandler;
import net.hserver.hp.server.service.AppService;
import net.hserver.hp.server.service.StatisticsService;
import org.beetl.sql.core.page.PageResult;
import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.core.server.util.JsonResult;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hxm
 */
@Controller
public class IndexController {

    @Autowired
    private StatisticsService statisticsService;

    @GET("/")
    public void index(Integer page, HttpResponse response) {
        if (page == null) {
            page = 1;
        }
        PageResult<StatisticsEntity> list = statisticsService.list(page, 10);
        Map<String, Object> data = new HashMap<>(5);
        data.put("page", page);
        data.put("pageSize", 10);
        data.put("totalRow", list.getTotalRow());
        data.put("list", list.getList());
        data.put("totalPage", list.getTotalPage());
        data.put("statisticsSize", HpServerHandler.CURRENT_STATUS.size());
        data.put("statisticsData", HpServerHandler.CURRENT_STATUS);
        response.sendTemplate("/index.ftl", data);
    }

    @GET("/statistics/remove")
    public void remove(Integer page, HttpResponse response, String id) {
        if (id != null) {
            statisticsService.remove(id);
        }
        index(page, response);
    }

    @GET("/statistics/getMyInfo")
    public JsonResult remove(Integer page, String username) {
        PageResult<StatisticsEntity> list = statisticsService.list(page, 10, username);
        return list == null ? JsonResult.error() : JsonResult.ok().put("data", list);
    }

}
