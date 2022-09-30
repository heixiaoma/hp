package net.hserver.hp.server.controller.admin;

import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.annotation.POST;
import cn.hserver.plugin.web.context.PartFile;
import cn.hserver.plugin.web.interfaces.HttpRequest;
import cn.hserver.plugin.web.interfaces.HttpResponse;
import net.hserver.hp.server.domian.entity.AppEntity;
import net.hserver.hp.server.domian.entity.PayEntity;
import net.hserver.hp.server.service.AppService;
import net.hserver.hp.server.service.PayService;
import org.beetl.sql.core.page.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hxm
 */
@Controller
public class PayController {
    private static final Logger log = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private PayService payService;

    @GET("/admin/pay")
    public void index(Integer page, HttpResponse response) {
        if (page == null) {
            page = 1;
        }
        PageResult<PayEntity> list = payService.list(page, 10);
        Map<String, Object> data = new HashMap<>(5);
        data.put("page", page);
        data.put("pageSize", 10);
        data.put("totalRow", list.getTotalRow());
        data.put("list", list.getList());
        data.put("totalPage", list.getTotalPage());
        response.sendTemplate("/admin/pay.ftl", data);
    }

    @POST("/admin/pay/add")
    public void add(Integer page, HttpResponse response, PayEntity payEntity) {
        try {
            if (payEntity != null && payEntity.getPrice().trim().length() > 0 && payEntity.getUsername().trim().length() > 0) {
                payService.add(payEntity);
            }
        } catch (Exception ignored) {
        }
        index(page, response);
    }


    @GET("/admin/pay/remove")
    public void remove(Integer page, HttpResponse response, String id) {
        if (id != null) {
            payService.remove(id);
        }
        index(page, response);
    }
}
