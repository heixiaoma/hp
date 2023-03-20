package net.hserver.hp.server.task;

import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.core.ioc.annotation.Bean;
import cn.hserver.core.ioc.annotation.Task;
import net.hserver.hp.server.service.StatisticsService;
import net.hserver.hp.server.service.UserService;

import java.util.UUID;

import static net.hserver.hp.server.config.ConstConfig.REG_CODE;

@Bean
public class FlowTask {

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private UserService userService;

    /**
     * 一小时执行一次 3600*1000
     */
    @Task(name = "清除历史日志", time = "3600000")
    public void clearLog() {
        REG_CODE = UUID.randomUUID().toString();
        statisticsService.removeExpData();
        userService.removeExp();
    }

}
