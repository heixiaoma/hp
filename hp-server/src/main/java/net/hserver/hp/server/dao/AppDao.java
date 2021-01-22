package net.hserver.hp.server.dao;

import net.hserver.hp.server.domian.entity.AppEntity;
import net.hserver.hp.server.domian.entity.PortEntity;
import net.hserver.plugins.beetlsql.annotation.BeetlSQL;
import org.beetl.sql.mapper.BaseMapper;

/**
 * @author hxm
 */
@BeetlSQL
public interface AppDao extends BaseMapper<AppEntity> {

}
