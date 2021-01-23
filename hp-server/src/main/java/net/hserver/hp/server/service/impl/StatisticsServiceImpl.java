package net.hserver.hp.server.service.impl;

import net.hserver.hp.server.dao.StatisticsDao;
import net.hserver.hp.server.domian.bean.Statistics;
import net.hserver.hp.server.domian.entity.AppEntity;
import net.hserver.hp.server.domian.entity.StatisticsEntity;
import net.hserver.hp.server.service.StatisticsService;
import net.hserver.hp.server.utils.DateUtil;
import org.beetl.sql.core.page.PageResult;
import top.hserver.core.ioc.annotation.Autowired;
import top.hserver.core.ioc.annotation.Bean;

import java.util.UUID;

/**
 * @author hxm
 */
@Bean
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsDao statisticsDao;

    @Override
    public void add(Statistics statistics) {
        StatisticsEntity statisticsEntity = new StatisticsEntity();
        statisticsEntity.setId(UUID.randomUUID().toString());
        statisticsEntity.setCreateTime(String.valueOf(System.currentTimeMillis()));
        statisticsEntity.setReceive(String.valueOf(statistics.getReceive()));
        statisticsEntity.setSend(String.valueOf(statistics.getSend()));
        statisticsEntity.setPackNum(String.valueOf(statistics.getPackNum()));
        statisticsEntity.setPort(statistics.getPort());
        statisticsEntity.setUsername(statistics.getUsername());
        statisticsEntity.setConnectNum(String.valueOf(statistics.getConnectNum()));
        statisticsDao.insert(statisticsEntity);
    }

    @Override
    public PageResult<StatisticsEntity> list(Integer page, Integer pageSize) {
        PageResult<StatisticsEntity> create_time_desc = statisticsDao.createLambdaQuery().orderBy("create_time desc").page(page, pageSize);
        for (StatisticsEntity statisticsEntity : create_time_desc.getList()) {
            statisticsEntity.setCreateTime(DateUtil.stampToDate(statisticsEntity.getCreateTime()));
        }
        return create_time_desc;
    }

    @Override
    public PageResult<StatisticsEntity> list(Integer page, Integer pageSize, String username) {
        try {
            PageResult<StatisticsEntity> create_time_desc = statisticsDao.createLambdaQuery().andEq(StatisticsEntity::getUsername, username.trim()).orderBy("create_time desc").page(page, pageSize);
            for (StatisticsEntity statisticsEntity : create_time_desc.getList()) {
                statisticsEntity.setCreateTime(DateUtil.stampToDate(statisticsEntity.getCreateTime()));
            }
            return create_time_desc;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void remove(String id) {
        statisticsDao.deleteById(id);
    }
}
