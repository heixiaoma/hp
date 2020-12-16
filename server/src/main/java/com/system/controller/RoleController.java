package com.system.controller;

import com.system.domain.entity.RoleEntity;
import com.system.domain.dto.MpDTO;
import com.system.service.RoleService;
import top.hserver.core.ioc.annotation.*;
import top.hserver.core.server.util.JsonResult;

/**
 * @author hxm
 */
@Controller(value = "/system/role/", name = "角色管理")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequiresPermissions("get:/system/role/list")
    @GET("list")
    public JsonResult list() {
        return JsonResult.ok().put("data", roleService.list());
    }

    @RequiresPermissions("post:/system/role/add")
    @POST("add")
    public JsonResult add(RoleEntity roleEntity) {
        roleService.add(roleEntity);
        return JsonResult.ok();
    }

    @RequiresPermissions("get:/system/role/deleteRole")
    @GET("deleteRole")
    public JsonResult deleteRole(Integer id) {
        roleService.deleteRole(id);
        return JsonResult.ok();
    }

    @RequiresPermissions("post:/system/role/updateRole")
    @POST("updateRole")
    public JsonResult updateRole(RoleEntity roleEntity) {
        if (roleEntity.getId() != null) {
            roleService.updateRole(roleEntity);
            return JsonResult.ok();
        }
        return JsonResult.error();
    }

    @RequiresPermissions("post:/system/role/updateMenuPermission")
    @POST("updateMenuPermission")
    public JsonResult updateMenuPermission(MpDTO mpDTO) {
        if (mpDTO != null) {
            roleService.updateMenuPermission(mpDTO);
            return JsonResult.ok();
        }
        return JsonResult.error();
    }

    @RequiresPermissions("post:/system/role/updateModulePermission")
    @POST("updateModulePermission")
    public JsonResult updateModulePermission(MpDTO mpDTO) {
        if (mpDTO != null) {
            roleService.updateModulePermission(mpDTO);
            return JsonResult.ok();
        }
        return JsonResult.error();
    }

    @RequiresPermissions("get:/system/role/getMenuPermission")
    @GET("getMenuPermission")
    public JsonResult getMenuPermission(Integer roleId) {
        if (roleId != null) {
            return JsonResult.ok().put("data", roleService.getMenuPermission(roleId));
        }
        return JsonResult.error();
    }

    @RequiresPermissions("get:/system/role/getModulePermission")
    @GET("getModulePermission")
    public JsonResult getModulePermission(Integer roleId) {
        if (roleId != null) {
            return JsonResult.ok().put("data", roleService.getModulePermission(roleId));
        }
        return JsonResult.error();
    }
}
