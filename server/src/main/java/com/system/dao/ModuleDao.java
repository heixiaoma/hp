package com.system.dao;

import com.system.domain.entity.ModuleEntity;
import net.hserver.plugins.beetlsql.annotation.BeetlSQL;
import org.beetl.sql.mapper.BaseMapper;

/**
 * @author hxm
 */
@BeetlSQL
public interface ModuleDao extends BaseMapper<ModuleEntity> {

    default boolean deleteAll() {
        return executeUpdate("delete from sys_module") > 0;
    }
}
