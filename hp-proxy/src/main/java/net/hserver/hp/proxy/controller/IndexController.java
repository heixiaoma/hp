package net.hserver.hp.proxy.controller;

import cn.hserver.HServerApplication;
import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.core.server.context.ConstConfig;
import cn.hserver.core.server.util.JsonResult;
import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.interfaces.HttpRequest;
import cn.hserver.plugin.web.interfaces.HttpResponse;
import cn.hserver.plugin.web.interfaces.ProgressStatus;
import net.hserver.hp.proxy.annotation.CheckApi;
import net.hserver.hp.proxy.config.CostConfig;
import net.hserver.hp.proxy.config.WebConfig;
import net.hserver.hp.proxy.domian.bean.ConInfo;
import net.hserver.hp.proxy.domian.bean.GlobalStat;
import net.hserver.hp.proxy.handler.HpServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hxm
 */
@Controller
public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private WebConfig webConfig;

    /**
     * 大盘统计
     *
     * @param request
     * @param response
     */
    @CheckApi
    @GET("/statistics")
    public void index(HttpRequest request, HttpResponse response) {
        Map<String, Object> data = new HashMap<>(5);
        data.put("token", request.query("token"));
        data.put("host", webConfig.getUserHost());
        data.put("statisticsSize", HpServerHandler.CURRENT_STATUS.size());
        data.put("statisticsData", HpServerHandler.CURRENT_STATUS);
        StringBuilder flowType = new StringBuilder();
        StringBuilder flowConnectNum = new StringBuilder();
        StringBuilder flowReceive = new StringBuilder();
        StringBuilder flowSend = new StringBuilder();
        StringBuilder flowPackNum = new StringBuilder();
        for (int i = 0; i < GlobalStat.STATS.size(); i++) {
            GlobalStat.Stat stat = GlobalStat.STATS.get(i);
            flowType.append("\"").append(stat.getDate()).append("\"");
            flowConnectNum.append(stat.getConnectNum());
            flowReceive.append(stat.getReceive() / 1024);
            flowSend.append(stat.getSend() / 1024);
            flowPackNum.append(stat.getPackNum());
            if (i != GlobalStat.STATS.size() - 1) {
                flowType.append(",");
                flowConnectNum.append(",");
                flowReceive.append(",");
                flowSend.append(",");
                flowPackNum.append(",");
            }
        }
        data.put("flowType", flowType);
        data.put("flowConnectNum", flowConnectNum);
        data.put("flowReceive", flowReceive);
        data.put("flowSend", flowSend);
        data.put("flowPackNum", flowPackNum);
        response.sendTemplate("/index.ftl", data);
    }

    @CheckApi
    @GET("/backList")
    public void backList(HttpRequest request, HttpResponse response) {
        try {
            Map<String, Object> data = new HashMap<>(2);
            data.put("token", request.query("token"));
            data.put("dataSize", CostConfig.IP_USER.size());
            data.put("data", CostConfig.IP_USER);
            response.sendTemplate("/backList.ftl", data);
        }catch (Exception e){}
    }


    /**
     * 服务离线功能
     *
     * @param request
     * @param response
     */
    @CheckApi
    @GET("/clearBack")
    public void clearBack(HttpRequest request, HttpResponse response) {
        CostConfig.IP_USER.remove(request.query("ip"));
        backList(request, response);
    }

    @GET("/clearAll")
    public void clearAll(HttpRequest request, HttpResponse response) {
        CostConfig.IP_USER.clear();
        backList(request, response);
    }

    /**
     * 服务离线功能
     *
     * @param request
     * @param response
     */
    @CheckApi
    @GET("/offline")
    public void offline(HttpRequest request, HttpResponse response) {
        HpServerHandler.offline(request.query("domain"));
        index(request, response);
    }


}
