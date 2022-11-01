package net.hserver.hp.server.controller.admin;

import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.annotation.POST;
import cn.hserver.plugin.web.interfaces.HttpResponse;
import net.hserver.hp.server.domian.vo.UserVo;
import net.hserver.hp.server.service.UserService;
import net.hserver.hp.server.utils.UserCheckUtil;
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

    @GET("/admin/user")
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
        response.sendTemplate("/admin/user.ftl", data);
    }

    @POST("/admin/user/edit")
    public void edit(Integer page, HttpResponse response, String username, String password, String ports, Integer type,Integer level,String domains) {
        if (username != null) {
            userService.editUser(username, password, ports, type,level,domains);
        }
        index(page, response, null);
    }

    @POST("/admin/user/add")
    public void add(Integer page, HttpResponse response, String username, String password, String ports,String domains,Integer level) {
        if (username != null) {
            userService.addUser(username, password, ports,domains,level);
        }
        index(page, response, null);
    }

    @GET("/admin/user/remove")
    public void remove(Integer page, HttpResponse response, String username) {
        if (username != null) {
            userService.remove(username);
        }
        index(page, response, null);
    }
}
