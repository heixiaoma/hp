package net.hserver.hp.server.service;

import net.hserver.hp.server.domian.entity.AppEntity;
import net.hserver.hp.server.domian.entity.CoreEntity;
import org.beetl.sql.core.page.PageResult;

/**
 * @author hxm
 */
public interface CoreService {

    /**
     * 列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    PageResult<CoreEntity> list(Integer page, Integer pageSize);

    /**
     * 添加用户
     *
     * @param appEntity
     */
    boolean add(CoreEntity appEntity);

    /**
     * 删除
     *
     * @param id
     */
    void remove(String id);

    /**
     * 最新版本
     */
    CoreEntity getAppVersion();

}
