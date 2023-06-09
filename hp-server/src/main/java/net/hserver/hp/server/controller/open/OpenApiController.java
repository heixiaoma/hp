package net.hserver.hp.server.controller.open;

import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.core.queue.HServerQueue;
import cn.hserver.core.server.util.JsonResult;
import cn.hserver.plugin.web.annotation.Controller;
import cn.hserver.plugin.web.annotation.GET;
import cn.hserver.plugin.web.annotation.POST;
import cn.hserver.plugin.web.context.WebConstConfig;
import cn.hserver.plugin.web.interfaces.HttpRequest;
import cn.hserver.plugin.web.interfaces.HttpResponse;
import cn.hserver.plugin.web.interfaces.ProgressStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import net.hserver.hp.server.config.ConstConfig;
import net.hserver.hp.server.dao.DomainDao;
import net.hserver.hp.server.dao.PortDao;
import net.hserver.hp.server.domian.bean.Statistics;
import net.hserver.hp.server.domian.entity.*;
import net.hserver.hp.server.domian.vo.UserVo;
import net.hserver.hp.server.service.*;
import net.hserver.hp.server.utils.DateUtil;
import net.hserver.hp.server.utils.UserCheckUtil;
import org.beetl.sql.core.page.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private CoreService coreService;

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

    @GET("/user/email")
    public JsonResult sendEmail(String username) {
        if (!UserCheckUtil.checkUsername(username)){
            return JsonResult.error("注册只能使用qq邮箱");
        }
        //校验IP 5分钟一次逻辑
        if (ConstConfig.EMAIL_IP.getIfPresent(username) != null) {
            return JsonResult.error("已经发了邮件，请检查下垃圾邮箱里是否存在。");
        }
        ConstConfig.EMAIL_IP.put(username, username);
        HServerQueue.sendQueue("EMAIL", username);
        return JsonResult.ok();
    }

    @POST("/user/reg")
    public JsonResult reg(String username, String password, String code) {
        int time = ConstConfig.TIME;
        if (time == -1) {
            return JsonResult.error("注册功能已经关闭。如有疑问联系管理员");
        } else if (time > 0) {
            int hour = LocalDateTime.now().getHour();
            if (hour != time) {
                return JsonResult.error("注册功能已经关闭,请在每天的" + time + "点时注册，开放注册时间为一小时");
            }
        }
        if (username != null && password != null) {
            username = username.trim();
            if (!UserCheckUtil.checkUsername(username)) {
                return JsonResult.error("注册只能使用qq邮箱");
            }
            //如果没有用通用码注册就必须用邮箱码注册
            if (!ConstConfig.REG_CODE.equals(code)) {
                //校验邮箱验证码
                String email_code = ConstConfig.EMAIL_CODE.getIfPresent(username.trim());
                if (email_code == null || !email_code.equals(code)) {
                    return JsonResult.ok("验证码错误，请检查邮箱验证码");
                }
            }
            if (userService.addUser(username.trim(), password.trim(), null, null, 0,"false")) {
                return JsonResult.ok("注册成功");
            } else {
                return JsonResult.error("用户名已经存在请换一个");
            }
        }
        return JsonResult.error("注册失败");
    }


    @POST("/user/domainLogin")
    public JsonResult domainLogin(HttpRequest request, String username, String password, String domain, String address) throws JsonProcessingException {
        if (domain != null && password != null && username != null) {
            if (address == null) {
                address = request.getIpAddress();
            }
            log.info("登录信息：{}，{}，{}，{}，{}",username,password,domain,address,request.getIpAddress());

            UserVo login = userService.domainLogin(username, password, domain, address);
            if (login != null) {
                login.setTips(ConstConfig.TIPS);
                log.info(WebConstConfig.JSON.writeValueAsString(login));
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
            UserVo login = userService.login(username.trim(), password.trim(), address);
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
        if (port < 10000 || port > 60000) {
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
    public JsonResult domainAdd(String userId, String domain, String customDomain) {
        domain = domain.trim();
        if (domain.length() <= 3) {
            return JsonResult.error("域名的长度太短，大于等于4位");
        }
        if (!UserCheckUtil.checkDomain(domain)) {
            return JsonResult.error("域名只能小写字母和数字");
        }
        List<DomainEntity> ports = domainDao.createLambdaQuery().andEq(DomainEntity::getUserId, userId).select();
        if (ports.size() > ConstConfig.PROXY_SIZE - 1) {
            return JsonResult.error("限定每人" + ConstConfig.PROXY_SIZE + "个域名，域名过多，暂时不能过多申请");
        }
        List<DomainEntity> select = domainDao.createLambdaQuery().andEq(DomainEntity::getDomain, domain).select();
        if (select.isEmpty()) {
            DomainEntity portEntity = new DomainEntity();
            portEntity.setUserId(userId);
            portEntity.setId(UUID.randomUUID().toString());
            portEntity.setCreateTime(String.valueOf(System.currentTimeMillis()));
            portEntity.setDomain(domain);
            portEntity.setCustomDomain(customDomain);
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
        return JsonResult.ok().put("data", payService.getTop52());
    }


    @GET("/app/getVersion")
    public JsonResult getVersion() {
        return JsonResult.ok().put("data", appService.getAppVersion());
    }

    @GET("/app/getCoreVersion")
    public JsonResult getCoreVersion() {
        CoreEntity appVersion = coreService.getAppVersion();
        String createTime = appVersion.getCreateTime();
        String s = DateUtil.stampToDate(createTime);
        appVersion.setCreateTime(s);
        return JsonResult.ok().put("data",appVersion );
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
        }, request);
    }
}
