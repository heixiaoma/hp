package net.hserver.hp.server.controller.open;

import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.core.server.util.JsonResult;
import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.annotation.POST;
import net.hserver.hp.server.domian.entity.ConfigEntity;
import net.hserver.hp.server.domian.entity.UserEntity;
import net.hserver.hp.server.service.ConfigService;
import net.hserver.hp.server.service.UserService;

/**
 * 用户的配置中心
 */
@Controller("/config/")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @Autowired
    private UserService userService;

    @POST("save")
    public JsonResult save(ConfigEntity config){
        if (config.getUserId()==null){
            return JsonResult.error("用户ID不能为空");
        }
        if (config.getServerHost()==null){
            return JsonResult.error("穿透服务不能为空");
        }
        if (config.getType()==null){
            return JsonResult.error("穿透类型不能为空");
        }
        if (config.getType().contains("TCP")&&config.getDomain()==null){
            return JsonResult.error("域名不能为空");
        }
        if (config.getDeviceId()==null||config.getDeviceId().equals("NO_ID")){
            return JsonResult.error("设备ID不能为空");
        }
        if (config.getUserHost()==null){
            return JsonResult.error("内网服务不能为空");
        }

        UserEntity userById = userService.getUserById(config.getUserId());
        if (userById==null){
            return JsonResult.error("用户不存在");
        }
        config.setUsername(userById.getUsername());
        config.setPassword(userById.getPassword());

        if (configService.save(config)){
            return JsonResult.ok();
        }else {
            return JsonResult.error("一个账号只能配置3个自动穿透");
        }
    }

    @GET("list")
    public JsonResult list(String userId){
        return JsonResult.ok().put("data",configService.list(userId));
    }

    @GET("listDevice")
    public JsonResult listDevice(String deviceId){
        return JsonResult.ok().put("data",configService.listDevice(deviceId));
    }

    @GET("remove")
    public JsonResult remove(String id){
        return JsonResult.ok().put("data",configService.remove(id));
    }



}
