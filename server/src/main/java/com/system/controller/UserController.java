package com.system.controller;

import com.system.jwt.Token;
import com.system.jwt.TokenUtil;
import com.system.domain.dto.LoginDTO;
import com.system.domain.vo.LoginVO;
import com.system.domain.dto.UserDTO;
import com.system.domain.vo.UserInfoVO;
import com.system.service.TokenService;
import com.system.service.UserService;
import top.hserver.core.interfaces.HttpRequest;
import top.hserver.core.ioc.annotation.*;
import top.hserver.core.ioc.annotation.apidoc.ApiImplicitParam;
import top.hserver.core.ioc.annotation.apidoc.ApiImplicitParams;
import top.hserver.core.ioc.annotation.apidoc.DataType;
import top.hserver.core.server.util.JsonResult;

import java.util.List;

/**
 * @author hxm
 */
@Controller(value = "/system/user/", name = "用户管理")
public class UserController extends BaseController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @POST("login")
    @ApiImplicitParams(
            value = {
                    @ApiImplicitParam(
                            name = "password", value = "密码", required = true, dataType = DataType.String
                    ), @ApiImplicitParam(
                    name = "username", value = "用户名", required = true, dataType = DataType.String
            )
            },
            name = "登录接口",
            note = "后端的登录接口"
    )
    public JsonResult login(LoginDTO loginDTO) {
        LoginVO login = userService.login(loginDTO);
        return login == null ? JsonResult.error("登录失败") : JsonResult.ok().put("data", login);
    }

    @RequiresPermissions("get:/system/user/info")
    @GET("info")
    public JsonResult info(String token) {
        Token tokenInfo = TokenUtil.getToken(token);
        UserInfoVO userInfo = userService.getUserInfo(tokenInfo.getUserId());
        return JsonResult.ok().put("data", userInfo);
    }

    @RequiresPermissions("post:/system/user/logout")
    @POST("logout")
    public JsonResult logout(HttpRequest request) {
        Integer userId = getUserId(request);
        tokenService.removeToken(userId);
        return JsonResult.ok();
    }

    @RequiresPermissions("get:/system/user/list")
    @GET("list")
    public JsonResult list(Integer page, Integer pageSize) {
        return JsonResult.ok().put("data", userService.list(page, pageSize));
    }

    @RequiresPermissions("post:/system/user/edit")
    @POST("edit")
    public JsonResult edit(UserDTO userDTO) {
        userService.edit(userDTO);
        return JsonResult.ok();
    }

    @RequiresPermissions("post:/system/user/add")
    @POST("add")
    public JsonResult add(UserDTO userDTO) {
        userService.add(userDTO);
        return JsonResult.ok();
    }


    @RequiresPermissions("get:/system/user/delete")
    @GET("delete")
    public JsonResult delete(Integer id) {
        userService.delete(id);
        return JsonResult.ok();
    }

    @RequiresPermissions("post:/system/user/multiDelete")
    @POST("multiDelete")
    public JsonResult multiDelete(List<Integer> ids) {
        userService.multiDelete(ids);
        return JsonResult.ok();
    }

}
