package com.system.dao;

import com.system.domain.entity.TokenEntity;
import net.hserver.plugins.beetlsql.annotation.BeetlSQL;
import org.beetl.sql.mapper.BaseMapper;

/**
 * @author hxm
 */
@BeetlSQL
public interface TokenDao extends BaseMapper<TokenEntity> {

    /**
     * 删除这个用户的所有ID
     *
     * @param userId
     * @return
     */
    default boolean removeToken(Integer userId) {
        int i = executeUpdate("delete from sys_token where user_id=?", userId);
        return i > 0;
    }
}
