package com.system.controller;

import com.system.domain.entity.MenuEntity;
import com.system.service.MenuService;
import top.hserver.core.interfaces.HttpRequest;
import top.hserver.core.ioc.annotation.*;
import top.hserver.core.server.util.JsonResult;

/**
 * @author hxm
 */
@Controller(value = "/system/menu/", name = "菜单管理")
public class MenuController extends BaseController {

    @Autowired
    protected MenuService menuService;


    @RequiresPermissions("get:/system/menu/list")
    @GET("list")
    public JsonResult list() {
        return JsonResult.ok().put("data", menuService.list());
    }


    @RequiresPermissions("get:/system/menu/menu")
    @GET("menu")
    public JsonResult menu(HttpRequest request) {
        return JsonResult.ok().put("data", menuService.menu(getRoleIds(request)));
    }



    @RequiresPermissions("get:/system/menu/listTop")
    @GET("listTop")
    public JsonResult listTop() {
        return JsonResult.ok().put("data", menuService.listTop());
    }

    @RequiresPermissions("post:/system/menu/updateMenu")
    @POST("updateMenu")
    public JsonResult updateMenu(MenuEntity menuEntity) {
        if (menuEntity.getId() != null) {
            menuService.updateMenu(menuEntity);
            return JsonResult.ok();
        }
        return JsonResult.error();
    }

    @RequiresPermissions("post:/system/menu/addMenu")
    @POST("addMenu")
    public JsonResult addMenu(MenuEntity menuEntity) {
        menuService.addMenu(menuEntity);
        return JsonResult.ok();
    }

    @RequiresPermissions("get:/system/menu/deleteMenu")
    @GET("deleteMenu")
    public JsonResult deleteMenu(Integer id) {
        menuService.deleteMenu(id);
        return JsonResult.ok();
    }

}
