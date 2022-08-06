package net.hserver.hp.server.controller;

import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.annotation.POST;
import cn.hserver.plugin.web.interfaces.HttpResponse;
import net.hserver.hp.server.domian.vo.UserVo;
import net.hserver.hp.server.service.UserService;
import org.beetl.sql.core.page.PageResult;
import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.core.server.util.JsonResult;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hxm
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GET("/user")
    public void index(Integer page, HttpResponse response, String username) {
        if (page == null) {
            page = 1;
        }
        if (username == null) {
            username = "";
        }

        PageResult<UserVo> list = userService.list(page, 10, username);
        Map<String, Object> data = new HashMap<>(5);
        data.put("page", page);
        data.put("pageSize", 10);
        data.put("totalRow", list.getTotalRow());
        data.put("list", list.getList());
        data.put("totalPage", list.getTotalPage());
        data.put("username", username);
        response.sendTemplate("/user.ftl", data);
    }

    @POST("/user/reg")
    public JsonResult reg(String username, String password) {
        if (username != null && password != null) {
            if (username.trim().length() < 5) {
                return JsonResult.error("注册的长度太短");
            }
            if (userService.addUser(username.trim(), password.trim(), null)) {
                return JsonResult.ok("注册成功");
            } else {
                return JsonResult.error("用户名已经存在请换一个");
            }
        }
        return JsonResult.error("登录失败");
    }

    @POST("/user/login")
    public JsonResult login(String username, String password) {
        if (username != null && password != null) {
            UserVo login = userService.login(username, password);
            if (login != null) {
                return JsonResult.ok("登录成功").put("data", login);
            }
        }
        return JsonResult.error("登录失败");
    }


    @POST("/user/edit")
    public void edit(Integer page, HttpResponse response, String username, String password, String ports, Integer type) {
        if (username != null) {
            userService.editUser(username, password, ports, type);
        }
        index(page, response, null);
    }

    @POST("/user/add")
    public void add(Integer page, HttpResponse response, String username, String password, String ports) {
        if (username != null) {
            userService.addUser(username, password, ports);
        }
        index(page, response, null);
    }

    @GET("/user/remove")
    public void remove(Integer page, HttpResponse response, String username) {
        if (username != null) {
            userService.remove(username);
        }
        index(page, response, null);
    }
}
