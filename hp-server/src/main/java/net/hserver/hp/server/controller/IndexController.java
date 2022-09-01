package net.hserver.hp.server.controller;

import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.interfaces.HttpResponse;
import net.hserver.hp.server.config.WebConfig;
import net.hserver.hp.server.domian.bean.GlobalStat;
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
    private WebConfig webConfig;
    @Autowired
    private StatisticsService statisticsService;

    @GET("/admin")
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
        data.put("host", webConfig.getHost());
        data.put("statisticsSize", HpServerHandler.CURRENT_STATUS.size());
        data.put("statisticsData", HpServerHandler.CURRENT_STATUS);

        StringBuilder flowType=new StringBuilder();
        StringBuilder flowConnectNum=new StringBuilder();
        StringBuilder flowReceive=new StringBuilder();
        StringBuilder flowSend=new StringBuilder();
        StringBuilder flowPackNum=new StringBuilder();

        for (int i = 0; i < GlobalStat.STATS.size(); i++) {
            GlobalStat.Stat stat = GlobalStat.STATS.get(i);
            flowType.append("\"").append(stat.getDate()).append("\"");
            flowConnectNum.append(stat.getConnectNum());
            flowReceive.append(stat.getReceive()/1024);
            flowSend.append(stat.getSend()/1024);
            flowPackNum.append(stat.getPackNum());
            if (i!=GlobalStat.STATS.size()-1){
                flowType.append(",");
                flowConnectNum.append(",");
                flowReceive.append(",");
                flowSend.append(",");
                flowPackNum.append(",");
            }
        }
        data.put("flowType",flowType);
        data.put("flowConnectNum",flowConnectNum);
        data.put("flowReceive",flowReceive);
        data.put("flowSend",flowSend);
        data.put("flowPackNum",flowPackNum);
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


    @GET("/set/tips")
    public JsonResult addTips(String tips) {
        HpServerHandler.tips = tips;
        return JsonResult.ok(HpServerHandler.tips);
    }

    @GET("/get/tips")
    public JsonResult getTips() {
        return JsonResult.ok(HpServerHandler.tips);
    }

}
