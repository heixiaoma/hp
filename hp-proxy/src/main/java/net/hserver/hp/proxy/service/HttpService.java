package net.hserver.hp.proxy.service;

import net.hserver.hp.proxy.domian.vo.UserVo;
import okhttp3.OkHttpClient;
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


    public static UserVo login(String username, String password, String domain,String address) {
        log.info("登录信息：{}，{}，{}，{}",username,password,domain,address);
        UserVo userVo = new UserVo();
        userVo.setUsername(username);
        userVo.setPassword(password);
        userVo.setType(1);
        return userVo;
    }


}
