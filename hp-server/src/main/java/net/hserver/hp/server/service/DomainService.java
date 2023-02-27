
package net.hserver.hp.server.service;

import net.hserver.hp.server.domian.bean.DomainVo;
import net.hserver.hp.server.domian.entity.PayEntity;
import org.beetl.sql.core.page.PageResult;


/**
 * @author hxm
 */
public interface DomainService {

    /**
     * 列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    PageResult<DomainVo> list(Integer page, Integer pageSize,String username);

    /**
     * 添加用户
     *
     * @param appEntity
     */
    boolean add(DomainVo appEntity);

    /**
     * 删除
     *
     * @param id
     */
    void remove(String id);

}
