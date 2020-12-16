package com.system.dao;

import com.system.domain.entity.RoleEntity;
import net.hserver.plugins.beetlsql.annotation.BeetlSQL;
import org.beetl.sql.mapper.BaseMapper;

/**
 * @author hxm
 */
@BeetlSQL
public interface RoleDao extends BaseMapper<RoleEntity> {

}
