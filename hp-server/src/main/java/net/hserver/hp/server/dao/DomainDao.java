package net.hserver.hp.server.dao;

import cn.hserver.plugin.beetlsql.annotation.BeetlSQL;
import net.hserver.hp.server.domian.entity.DomainEntity;
import net.hserver.hp.server.domian.entity.PortEntity;
import org.beetl.sql.mapper.BaseMapper;

/**
 * @author hxm
 */
@BeetlSQL
public interface DomainDao extends BaseMapper<DomainEntity> {


    default int deleteByUserId(String userId){
        return executeUpdate("delete from sys_domain where user_id='"+userId+"'");
    }

}
