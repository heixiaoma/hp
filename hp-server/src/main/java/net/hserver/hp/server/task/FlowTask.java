package net.hserver.hp.server.task;

import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.core.ioc.annotation.Bean;
import cn.hserver.core.ioc.annotation.Task;
import net.hserver.hp.server.domian.bean.GlobalStat;
import net.hserver.hp.server.service.StatisticsService;
import net.hserver.hp.server.service.UserService;

@Bean
public class FlowTask {

    @Autowired
    private StatisticsService statisticsService;


    @Autowired
    private UserService userService;


    @Task(name = "5秒流量统计", time = "5000")
    public void flow() {
        GlobalStat.clear();
    }


    /**
     * 一小时执行一次 3600*1000
     */
    @Task(name = "清除历史日志", time = "3600000")
    public void clearLog() {
        statisticsService.removeExpData();
        userService.removeExp();
    }

}
