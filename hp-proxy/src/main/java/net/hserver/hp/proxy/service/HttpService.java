package net.hserver.hp.proxy.service;

import cn.hserver.core.ioc.IocUtil;
import cn.hserver.core.server.context.ConstConfig;
import cn.hserver.core.server.util.ExceptionUtil;
import cn.hserver.plugin.web.context.WebConstConfig;
import net.hserver.hp.proxy.config.WebConfig;
import net.hserver.hp.proxy.domian.bean.GlobalStat;
import net.hserver.hp.proxy.domian.vo.UserVo;
import net.hserver.hp.proxy.handler.HpServerHandler;
import okhttp3.FormBody;
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



    public static UserVo login(String username, String password){
        WebConfig bean = IocUtil.getBean(WebConfig.class);
        String adminAddress = bean.getAdminAddress();
        return null;
    }


    public static void updateLogin(String username, String address) {

    }


    public static void reg(){
        WebConfig bean = IocUtil.getBean(WebConfig.class);
        String adminAddress = bean.getAdminAddress();
        try {
            RequestBody body = new FormBody.Builder()
                    .add("name", bean.getName())
                    .add("ip", bean.getHost())
                    .add("port", WebConstConfig.JSON.writeValueAsString(ConstConfig.PORTS))
                    .add("num", String.valueOf(HpServerHandler.CURRENT_STATUS.size()))
                    .build();

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(adminAddress+"/open/proxy/reg")
                    .post(body)
                    .build();
            String string = okHttpClient.newCall(request).execute().body().string();
            log.info("reg：{}", string);
        } catch (Exception e) {
            log.info("reg：{}", ExceptionUtil.getMessage(e));
        }

    }

}
