package net.hserver.hp.proxy.service;

import cn.hserver.core.ioc.IocUtil;
import cn.hserver.core.server.util.ExceptionUtil;
import cn.hserver.core.server.util.PropUtil;
import cn.hserver.plugin.web.context.WebConstConfig;
import com.fasterxml.jackson.databind.JsonNode;
import net.hserver.hp.proxy.config.CostConfig;
import net.hserver.hp.proxy.config.WebConfig;
import net.hserver.hp.proxy.domian.bean.Statistics;
import net.hserver.hp.proxy.domian.vo.UserVo;
import net.hserver.hp.proxy.handler.HpServerHandler;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class HttpService {
    private static final Logger log = LoggerFactory.getLogger(HttpService.class);

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .callTimeout(120, TimeUnit.SECONDS)
            .pingInterval(5, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();


    public static UserVo login(String username, String password,String address) {
        WebConfig bean = IocUtil.getBean(WebConfig.class);
        String adminAddress = bean.getAdminAddress();
        try {
            RequestBody body = new FormBody.Builder()
                    .add("username", username)
                    .add("password", password)
                    .add("address", address)
                    .build();
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(adminAddress + "/user/login")
                    .post(body)
                    .build();
            String string = okHttpClient.newCall(request).execute().body().string();
            log.info("login：{}", string);
            JsonNode jsonNode = WebConstConfig.JSON.readTree(string);
            int code = jsonNode.get("code").asInt();
            if (code == 200) {
                return WebConstConfig.JSON.readValue(WebConstConfig.JSON.writeValueAsString(jsonNode.get("data")), UserVo.class);
            }
        } catch (Exception e) {
            log.info("login：{}", ExceptionUtil.getMessage(e));
        }
        return null;
    }


    public static void updateStatistics(Statistics statistics) {
        WebConfig bean = IocUtil.getBean(WebConfig.class);
        String adminAddress = bean.getAdminAddress();
        try {
            RequestBody requestBody = RequestBody.create(
                    MediaType.parse("application/json; charset=utf-8"),
                    WebConstConfig.JSON.writeValueAsBytes(statistics)
            );
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(adminAddress + "/statistics/add")
                    .post(requestBody)
                    .build();
            String string = okHttpClient.newCall(request).execute().body().string();
            log.info("statistics：{}", string);
        } catch (Exception e) {
            log.info("statistics：{}", ExceptionUtil.getMessage(e));
        }
    }


    public static void reg() {
        WebConfig bean = IocUtil.getBean(WebConfig.class);
        if (bean.getNotReg()!=null&&bean.getNotReg()){
            return;
        }
        String adminAddress = bean.getAdminAddress();
        try {
            RequestBody body = new FormBody.Builder()
                    .add("name", bean.getName())
                    .add("ip", bean.getHost())
                    .add("port", PropUtil.getInstance().get("port"))
                    .add("num", String.valueOf(HpServerHandler.CURRENT_STATUS.size()))
                    .build();

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(adminAddress + "/proxy/reg")
                    .post(body)
                    .build();
            String string = okHttpClient.newCall(request).execute().body().string();
            log.info("reg：{}", string);
            JsonNode jsonNode = WebConstConfig.JSON.readTree(string);
            if (jsonNode.get("code").asInt() == 200) {
                CostConfig.VER_TOKEN = jsonNode.get("msg").asText();
            }
        } catch (Exception e) {
            log.info("reg：{}", ExceptionUtil.getMessage(e));
        }
    }
}
