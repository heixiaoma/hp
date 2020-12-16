package com.system.controller;

import com.system.service.ModuleService;
import top.hserver.core.ioc.annotation.Autowired;
import top.hserver.core.ioc.annotation.Controller;
import top.hserver.core.ioc.annotation.GET;
import top.hserver.core.ioc.annotation.RequiresPermissions;
import top.hserver.core.server.util.JsonResult;


/**
 * @author hxm
 */
@Controller(value = "/system/module/", name = "模块管理")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @RequiresPermissions("get:/system/module/sync")
    @GET("sync")
    public JsonResult sync() {
        moduleService.sync();
        return JsonResult.ok();
    }

    @GET("list")
    @RequiresPermissions("get:/system/module/list")
    public JsonResult list() {
        return JsonResult.ok().put("data", moduleService.list());
    }

    @GET("getModuleList")
    @RequiresPermissions("get:/system/module/getModuleList")
    public JsonResult getModuleList() {
        return JsonResult.ok().put("data", moduleService.getModuleList());
    }
}
