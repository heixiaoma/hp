package net.hserver.hp.server.controller.open;

import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.core.server.util.JsonResult;
import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.annotation.POST;
import cn.hserver.plugin.web.interfaces.HttpRequest;
import cn.hserver.plugin.web.interfaces.HttpResponse;
import cn.hserver.plugin.web.interfaces.ProgressStatus;
import net.hserver.hp.server.config.ConstConfig;
import net.hserver.hp.server.dao.DomainDao;
import net.hserver.hp.server.dao.PortDao;
import net.hserver.hp.server.domian.bean.Statistics;
import net.hserver.hp.server.domian.entity.DomainEntity;
import net.hserver.hp.server.domian.entity.PortEntity;
import net.hserver.hp.server.domian.entity.ProxyServerEntity;
import net.hserver.hp.server.domian.entity.StatisticsEntity;
import net.hserver.hp.server.domian.vo.UserVo;
import net.hserver.hp.server.service.AppService;
import net.hserver.hp.server.service.PayService;
import net.hserver.hp.server.service.StatisticsService;
import net.hserver.hp.server.service.UserService;
import net.hserver.hp.server.utils.UserCheckUtil;
import org.beetl.sql.core.page.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
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

    @Autowired
    private PortDao portDao;

    @Autowired
    private DomainDao domainDao;

    @Autowired
    private PayService payService;

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
        int time = ConstConfig.TIME;
        if (time == -1) {
            return JsonResult.error("注册功能已经关闭。如有疑问联系管理员");
        } else if (time > 0) {
            Date date = new Date();
            if (date.getHours() != time) {
                return JsonResult.error("注册功能已经关闭,请在每天的" + time + "点时注册，开放注册时间为一小时");
            }
        }
        if (username != null && password != null) {
            //todo  检查特殊符号
            username = username.trim();
            if (username.length() <= 5) {
                return JsonResult.error("注册的长度太短，大于等于6位");
            }
            if (!UserCheckUtil.checkUsername(username)) {
                return JsonResult.error("注册只能小写字母和数字");
            }
            if (userService.addUser(username.trim(), password.trim(), null, null, 0)) {
                return JsonResult.ok("注册成功");
            } else {
                return JsonResult.error("用户名已经存在请换一个");
            }
        }
        return JsonResult.error("注册失败");
    }


    @POST("/user/domainLogin")
    public JsonResult domainLogin(HttpRequest request, String username, String password,String domain, String address){
        if (domain != null && password != null) {
            if (address == null) {
                address = request.getIpAddress();
            }
            UserVo login = userService.domainLogin(username, password,domain, address);
            if (login != null) {
                login.setTips(ConstConfig.TIPS);
                return JsonResult.ok("登录成功.").put("data", login);
            }
        }
        return JsonResult.error("登录失败.请尝试重新注册");
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
        return JsonResult.ok().put("data", sort);
    }


    @POST("/server/portAdd")
    public JsonResult portAdd(String userId, Integer port) {
        if (port < 10000||port>60000) {
            return JsonResult.error("10000-60000以内为开放端口。请重新申请");
        }
        List<PortEntity> ports = portDao.createLambdaQuery().andEq(PortEntity::getUserId, userId).select();
        if (ports.size() > 1) {
            return JsonResult.error("端口过多，暂时不能过多申请");
        }
        List<PortEntity> select = portDao.createLambdaQuery().andEq(PortEntity::getPort, port).select();
        if (select.isEmpty()) {
            PortEntity portEntity = new PortEntity();
            portEntity.setUserId(userId);
            portEntity.setId(UUID.randomUUID().toString());
            portEntity.setCreateTime(String.valueOf(System.currentTimeMillis()));
            portEntity.setPort(port);
            portDao.insert(portEntity);
            return JsonResult.ok();
        } else {
            return JsonResult.error("端口已经存在");
        }
    }


    @GET("/server/portList")
    public JsonResult portList(String userId) {
        List<PortEntity> ports = portDao.createLambdaQuery().andEq(PortEntity::getUserId, userId).select();
        return JsonResult.ok().put("data", ports);
    }

    @POST("/server/portRemove")
    public JsonResult portRemove(String userId, Integer port) {
        List<PortEntity> select = portDao.createLambdaQuery().andEq(PortEntity::getUserId, userId).andEq(PortEntity::getPort, port).select();
        if (!select.isEmpty()) {
            portDao.deleteById(select.get(0).getId());
            return JsonResult.ok();
        }
        return JsonResult.error();
    }


    @POST("/server/domainAdd")
    public JsonResult domainAdd(String userId, String domain) {
        domain = domain.trim();
        if (domain.length() <= 5) {
            return JsonResult.error("域名的长度太短，大于等于6位");
        }
        if (!UserCheckUtil.checkUsername(domain)) {
            return JsonResult.error("域名只能小写字母和数字");
        }
        List<DomainEntity> ports = domainDao.createLambdaQuery().andEq(DomainEntity::getUserId, userId).select();
        if (ports.size() > 1) {
            return JsonResult.error("域名过多，暂时不能过多申请");
        }
        List<DomainEntity> select = domainDao.createLambdaQuery().andEq(DomainEntity::getDomain, domain).select();
        if (select.isEmpty()) {
            DomainEntity portEntity = new DomainEntity();
            portEntity.setUserId(userId);
            portEntity.setId(UUID.randomUUID().toString());
            portEntity.setCreateTime(String.valueOf(System.currentTimeMillis()));
            portEntity.setDomain(domain);
            domainDao.insert(portEntity);
            return JsonResult.ok();
        } else {
            return JsonResult.error("域名已经存在");
        }
    }


    @GET("/server/domainList")
    public JsonResult domainList(String userId) {
        List<DomainEntity> ports = domainDao.createLambdaQuery().andEq(DomainEntity::getUserId, userId).select();
        return JsonResult.ok().put("data", ports);
    }

    @POST("/server/domainRemove")
    public JsonResult domainRemove(String userId, String domain) {
        List<DomainEntity> select = domainDao.createLambdaQuery().andEq(DomainEntity::getUserId, userId).andEq(DomainEntity::getDomain, domain).select();
        if (!select.isEmpty()) {
            domainDao.deleteById(select.get(0).getId());
            return JsonResult.ok();
        }
        return JsonResult.error();
    }

    @GET("/server/pay")
    public JsonResult pay() {
        return JsonResult.ok().put("data",payService.getTop50());
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
        }, request.getCtx());
    }
}
