package net.hserver.hp.server.dao;

import net.hserver.hp.server.domian.entity.UserEntity;
import net.hserver.plugins.beetlsql.annotation.BeetlSQL;
import org.beetl.sql.mapper.BaseMapper;

/**
 * @author hxm
 */
@BeetlSQL
public interface UserDao extends BaseMapper<UserEntity> {
}
