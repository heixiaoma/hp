package net.hserver.hp.proxy.controller;

import cn.hserver.HServerApplication;
import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.core.server.util.JsonResult;
import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.interfaces.HttpRequest;
import cn.hserver.plugin.web.interfaces.HttpResponse;
import cn.hserver.plugin.web.interfaces.ProgressStatus;
import net.hserver.hp.proxy.annotation.CheckApi;
import net.hserver.hp.proxy.config.WebConfig;
import net.hserver.hp.proxy.domian.bean.GlobalStat;
import net.hserver.hp.proxy.handler.HpServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
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
    @GET("/offline")
    public void offline(HttpRequest request, HttpResponse response) {
        HpServerHandler.offline(request.query("domain"));
        index(request, response);
    }

// 下面是版本过度做的调整

    @GET("/app/getVersion")
    public JsonResult getVersion() {
        HashMap<Object, Object> data = new HashMap<>();
        data.put("versionCode", "8.0");
        data.put("updateContent", "服务端全新升级，针对服务器后期的代理服务器集群，做了优化，后期会不定时上线其他更多的穿透服务，请保证本次的升级完成，才能持续享受更好的服务");
        return JsonResult.ok().put("data", data);
    }

    @GET("/app/download")
    public void download(HttpResponse response, HttpRequest request) throws Exception {

        File file = new File("../hp/hp-client.apk");
        response.setDownloadBigFile(file, new ProgressStatus() {

            @Override
            public void operationComplete(String s) {
                log.info("下载完成");
            }

            @Override
            public void downloading(long progress, long total) {
                if (total < 0) {
                    log.warn("file {} transfer progress: {}", file.getName(), progress);
                } else {
                    log.debug("file {} transfer progress: {}/{}", file.getName(), progress, total);
                }
            }
        }, request.getCtx());
    }

}
