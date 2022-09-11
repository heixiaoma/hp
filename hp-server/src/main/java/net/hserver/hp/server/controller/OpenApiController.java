package net.hserver.hp.server.controller;

import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.core.server.util.JsonResult;
import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.annotation.POST;
import cn.hserver.plugin.web.interfaces.HttpRequest;
import cn.hserver.plugin.web.interfaces.HttpResponse;
import cn.hserver.plugin.web.interfaces.ProgressStatus;
import net.hserver.hp.server.config.ConstConfig;
import net.hserver.hp.server.domian.bean.Statistics;
import net.hserver.hp.server.domian.entity.ProxyServerEntity;
import net.hserver.hp.server.domian.entity.StatisticsEntity;
import net.hserver.hp.server.domian.vo.UserVo;
import net.hserver.hp.server.service.AppService;
import net.hserver.hp.server.service.StatisticsService;
import net.hserver.hp.server.service.UserService;
import net.hserver.hp.server.utils.UserCheckUtil;
import org.beetl.sql.core.page.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 开放大盘的数据
 */
@Controller
public class OpenApiController {
    private static final Logger log = LoggerFactory.getLogger(OpenApiController.class);

    @Autowired
    private AppService appService;
    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private UserService userService;

    /**
     * 开放给用得接口
     *
     * @param page
     * @param username
     * @return
     */
    @GET("/statistics/getMyInfo")
    public JsonResult getMyInfo(Integer page, String username) {
        if (page == null) {
            page = 1;
        }
        PageResult<StatisticsEntity> list = statisticsService.list(page, 10, username);
        return list == null ? JsonResult.error() : JsonResult.ok().put("data", list);
    }

    @POST("/proxy/reg")
    public JsonResult regServer(ProxyServerEntity proxyServerEntity) {
        ProxyServerEntity.add(proxyServerEntity);
        return JsonResult.ok(ConstConfig.REG_TOKEN);
    }

    @POST("/user/reg")
    public JsonResult reg(String username, String password) {
        if (username != null && password != null) {
            //todo  检查特殊符号
            username = username.trim();
            if (username.length() <= 5) {
                return JsonResult.error("注册的长度太短");
            }
            if (!UserCheckUtil.checkUsername(username)) {
                return JsonResult.error("注册只能小写字母和数字");
            }
            if (userService.addUser(username.trim(), password.trim(), null)) {
                return JsonResult.ok("注册成功");
            } else {
                return JsonResult.error("用户名已经存在请换一个");
            }
        }
        return JsonResult.error("注册失败");
    }

    @POST("/user/login")
    public JsonResult login(HttpRequest request, String username, String password, String address) {
        if (username != null && password != null) {
            if (address == null) {
                address = request.getIpAddress();
            }
            UserVo login = userService.login(username, password, address);
            if (login != null) {
                login.setTips(ConstConfig.TIPS);
                return JsonResult.ok("登录成功.").put("data", login);
            }
        }
        return JsonResult.error("登录失败.请尝试重新注册");
    }

    @POST("/statistics/add")
    public JsonResult statisticsAdd(Statistics statistics) {
        System.out.println(statistics);
        statisticsService.add(statistics);
        return JsonResult.ok();
    }


    @GET("/load/data")
    public JsonResult data() {
        Collection<ProxyServerEntity> all = ProxyServerEntity.getAll();
        List<ProxyServerEntity> sort = all.stream().sorted(Comparator.comparing(ProxyServerEntity::getNum)).collect(Collectors.toList());
        if (sort.isEmpty()) {
            return JsonResult.error();
        }
        return JsonResult.ok().put("ip", sort.get(0).getIp()).put("port",sort.get(0).getPort());
    }


    @GET("/app/getVersion")
    public JsonResult getVersion() {
        return JsonResult.ok().put("data", appService.getAppVersion());
    }

    @GET("/app/download")
    public void download(HttpResponse response, HttpRequest request) throws Exception {

        File file = new File("./hp-client.apk");
        response.setDownloadBigFile(file, new ProgressStatus() {

            @Override
            public void operationComplete(String s) {
                log.info("下载完成");
            }

            @Override
            public void downloading(long progress, long total) {
                if (total < 0) {
                    log.warn("file {} transfer progress: {}", file.getName(), progress);
                } else {
                    log.debug("file {} transfer progress: {}/{}", file.getName(), progress, total);
                }
            }
        },request.getCtx());
    }
}
