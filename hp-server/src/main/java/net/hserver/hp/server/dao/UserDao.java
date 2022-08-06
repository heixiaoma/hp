package net.hserver.hp.server.dao;

import net.hserver.hp.server.domian.entity.UserEntity;
import cn.hserver.plugin.beetlsql.annotation.BeetlSQL;
import org.beetl.sql.mapper.BaseMapper;
import org.beetl.sql.mapper.annotation.Sql;
import org.beetl.sql.mapper.annotation.Update;

/**
 * @author hxm
 */
@BeetlSQL
public interface UserDao extends BaseMapper<UserEntity> {


    @Update
    @Sql("update sys_user set login_time=?,login_ip=?  where username = ?")
    void updateLogin(String loginTime,String ip,String username);

}
