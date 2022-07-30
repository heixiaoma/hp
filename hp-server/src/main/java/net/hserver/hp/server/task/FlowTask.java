package net.hserver.hp.server.task;

import cn.hserver.core.ioc.annotation.Bean;
import cn.hserver.core.ioc.annotation.Task;
import net.hserver.hp.server.domian.bean.GlobalStat;

@Bean
public class FlowTask {

    @Task(name = "5秒流量统计",time = "5000")
    public void flow(){
        GlobalStat.clear();
    }
}
