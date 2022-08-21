package net.hserver.hp.server.service;

import net.hserver.hp.server.domian.bean.Statistics;
import net.hserver.hp.server.domian.entity.AppEntity;
import net.hserver.hp.server.domian.entity.StatisticsEntity;
import org.beetl.sql.core.page.PageResult;

/**
 * @author hxm
 */
public interface StatisticsService {

    /**
     * 添加
     *
     * @param statistics
     */
    void add(Statistics statistics);


    /**
     * 列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    PageResult<StatisticsEntity> list(Integer page, Integer pageSize);

    /**
     * 按用户名查询
     *
     * @param page
     * @param pageSize
     * @param username
     * @return
     */
    PageResult<StatisticsEntity> list(Integer page, Integer pageSize, String username);


    /**
     * 删除
     *
     * @param id
     */
    void remove(String id);

    /**
     * 删除超过1个月的日志
     */
    void removeExpData();

}
