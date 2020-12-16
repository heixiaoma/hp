package com.system.controller;

import com.system.Application;
import top.hserver.core.api.ApiDoc;
import top.hserver.core.api.ApiResult;
import top.hserver.core.interfaces.HttpResponse;
import top.hserver.core.ioc.annotation.*;
import top.hserver.core.server.util.JsonResult;

import java.util.HashMap;
import java.util.List;

/**
 * @author hxm
 */
@Controller(value = "/system/api/", name = "API管理")
public class ApiController {

    @GET("index")
    public void index(HttpResponse httpResponse) {
        ApiDoc apiDoc = new ApiDoc(Application.class);
        try {
            List<ApiResult> apiData = apiDoc.getApiData();
            HashMap<String, Object> stringObjectHashMap = new HashMap<>(2);
            stringObjectHashMap.put("data", apiData);
            httpResponse.sendTemplate("api.ftl", stringObjectHashMap);
        } catch (Exception e) {
            httpResponse.sendJson(JsonResult.error());
        }
    }


}
