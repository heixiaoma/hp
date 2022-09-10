package net.hserver.hp.proxy.controller;

import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.interfaces.HttpResponse;
import net.hserver.hp.proxy.config.WebConfig;
import net.hserver.hp.proxy.domian.bean.GlobalStat;
import net.hserver.hp.proxy.handler.HpServerHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hxm
 */
@Controller
public class IndexController {

    @Autowired
    private WebConfig webConfig;

    @GET("/statistics")
    public void index(HttpResponse response) {
        Map<String, Object> data = new HashMap<>(5);
        data.put("host", webConfig.getHost());
        data.put("statisticsSize", HpServerHandler.CURRENT_STATUS.size());
        data.put("statisticsData",HpServerHandler.CURRENT_STATUS);
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


}
