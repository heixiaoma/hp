package net.hserver.hp.server.controller;

import net.hserver.hp.server.domian.vo.UserVo;
import net.hserver.hp.server.service.UserService;
import org.beetl.sql.core.page.PageResult;
import top.hserver.core.interfaces.HttpResponse;
import top.hserver.core.ioc.annotation.Autowired;
import top.hserver.core.ioc.annotation.Controller;
import top.hserver.core.ioc.annotation.GET;
import top.hserver.core.ioc.annotation.POST;

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
    public void index(Integer page, HttpResponse response) {
        if (page == null) {
            page = 1;
        }
        PageResult<UserVo> list = userService.list(page, 10);
        Map<String, Object> data = new HashMap<>(5);
        data.put("page", page);
        data.put("pageSize", 10);
        data.put("totalRow", list.getTotalRow());
        data.put("list", list.getList());
        data.put("totalPage", list.getTotalPage());
        response.sendTemplate("/user.ftl", data);
    }

    @POST("/user/edit")
    public void edit(Integer page, HttpResponse response, String username, String password, String ports) {
        if (username != null) {
            userService.editUser(username,password,ports);
        }
        index(page, response);
    }

    @POST("/user/add")
    public void add(Integer page, HttpResponse response, String username, String password, String ports) {
        if (username != null) {
            userService.addUser(username,password,ports);
        }
        index(page, response);
    }

    @GET("/user/remove")
    public void remove(Integer page, HttpResponse response, String username) {
        if (username != null) {
            userService.remove(username);
        }
        index(page, response);
    }
}
