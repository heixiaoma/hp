package net.hserver.hp.server.controller.admin;

import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.annotation.POST;
import cn.hserver.plugin.web.interfaces.HttpResponse;
import net.hserver.hp.server.domian.bean.DomainVo;
import net.hserver.hp.server.domian.entity.PayEntity;
import net.hserver.hp.server.service.DomainService;
import net.hserver.hp.server.service.PayService;
import org.beetl.sql.core.page.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hxm
 */
@Controller
public class DomainController {
    private static final Logger log = LoggerFactory.getLogger(DomainController.class);

    @Autowired
    private DomainService domainService;

    @GET("/admin/domain")
    public void index(Integer page,String usernameSearch, HttpResponse response) {
        if (page == null) {
            page = 1;
        }
        PageResult<DomainVo> list = domainService.list(page, 10,usernameSearch);
        Map<String, Object> data = new HashMap<>(5);
        data.put("page", page);
        data.put("pageSize", 10);
        if (usernameSearch==null){
            data.put("usernameSearch", "");
        }else {
            data.put("usernameSearch", usernameSearch);
        }
        data.put("totalRow", list.getTotalRow());
        data.put("list", list.getList());
        data.put("totalPage", list.getTotalPage());
        response.sendTemplate("/admin/domain.ftl", data);
    }

    @POST("/admin/domain/add")
    public void add(Integer page,String usernameSearch, HttpResponse response, DomainVo domainVo) {
        try {
            domainService.add(domainVo);
        } catch (Exception ignored) {
        }
        index(page, usernameSearch,response);
    }


    @GET("/admin/domain/remove")
    public void remove(Integer page,String usernameSearch, HttpResponse response, String id) {
        if (id != null) {
            domainService.remove(id);
        }
        index(page,usernameSearch, response);
    }
}
