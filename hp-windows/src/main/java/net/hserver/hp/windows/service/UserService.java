package net.hserver.hp.windows.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.hserver.hp.windows.config.ConstConfig;

import net.hserver.hp.windows.vo.UserVo;
import okhttp3.*;


public class UserService {
    /**
     * 登录
     *
     * @param username
     * @param password
     */
    public static UserVo login(String username, String password) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        //添加参数
        builder.addEncoded("username", username);
        builder.addEncoded("password", password);
        FormBody build = builder.build();
        final Request request = new Request.Builder()
                .url(ConstConfig.URL + "/user/login")
                .post(build)//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            Response execute = call.execute();
            JSONObject jsonObject = JSON.parseObject(execute.body().string());
            int code = jsonObject.getInteger("code");
            if (code == 200) {
                return JSON.parseObject(jsonObject.get("data").toString(), UserVo.class);
            }
        } catch (Exception e) {
        }
        return null;
    }


    public static boolean reg(String username, String password) throws Exception {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        //添加参数
        builder.addEncoded("username", username);
        builder.addEncoded("password", password);
        FormBody build = builder.build();
        final Request request = new Request.Builder()
                .url(ConstConfig.URL + "/user/reg")
                .post(build)//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);

        try {
            Response execute = call.execute();
            JSONObject jsonObject = JSON.parseObject(execute.body().string());
            int code = jsonObject.getInteger("code");
            if (code == 200) {
                return true;
            } else {
                throw new Exception(jsonObject.getString("msg"));
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
