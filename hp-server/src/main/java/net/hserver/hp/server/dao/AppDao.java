package net.hserver.hp.server.dao;

import cn.hserver.plugin.beetlsql.annotation.BeetlSQL;
import net.hserver.hp.server.domian.entity.AppEntity;
import org.beetl.sql.mapper.BaseMapper;

/**
 * @author hxm
 */
@BeetlSQL
public interface AppDao extends BaseMapper<AppEntity> {

}
