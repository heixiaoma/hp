package net.hserver.hp.server.controller.index;

import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.interfaces.HttpRequest;
import cn.hserver.plugin.web.interfaces.HttpResponse;
import net.hserver.hp.server.domian.entity.StatisticsEntity;
import net.hserver.hp.server.service.StatisticsService;
import org.beetl.sql.core.page.PageResult;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LogController {
    @Autowired
    private StatisticsService statisticsService;


    @GET("/index/log")
    public void log(Integer page, HttpRequest request, HttpResponse response) {
        if (page == null) {
            page = 1;
        }
        PageResult<StatisticsEntity> list = statisticsService.list(page, 10,request.getHeader("username"));
        Map<String, Object> data = new HashMap<>(5);
        data.put("page", page);
        data.put("pageSize", 10);
        data.put("totalRow", list.getTotalRow());
        data.put("list", list.getList());
        data.put("totalPage", list.getTotalPage());
        response.sendTemplate("/index/log.ftl", data);
    }

}
