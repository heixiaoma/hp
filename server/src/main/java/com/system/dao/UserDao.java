package com.system.dao;

import com.system.domain.entity.UserEntity;
import com.system.domain.vo.UserVO;
import net.hserver.plugins.beetlsql.annotation.BeetlSQL;
import org.beetl.sql.core.SQLReady;
import org.beetl.sql.core.page.PageResult;
import org.beetl.sql.mapper.BaseMapper;

import java.util.List;

/**
 * @author hxm
 */
@BeetlSQL
public interface UserDao extends BaseMapper<UserEntity> {


    default UserEntity findUserByUsername(String username) {
        List<UserEntity> select = createLambdaQuery().andEq(UserEntity::getUsername, username).select();
        if (select.isEmpty()) {
            return null;
        }
        return select.get(0);
    }

    default UserEntity findUserByUserId(Integer userId) {
        List<UserEntity> select = createLambdaQuery().andEq(UserEntity::getId, userId).select();
        if (select.isEmpty()) {
            return null;
        }
        return select.get(0);
    }

    default List<UserVO.UserRoleVO> selectRole(Integer userId) {
        return getSQLManager().execute(new SQLReady("SELECT sys_role.* FROM sys_role,sys_user_role WHERE sys_user_role.role_id=sys_role.id AND sys_user_role.user_id=?", userId), UserVO.UserRoleVO.class);
    }

    default PageResult<UserVO> list(long pageNumber, long pageSize) {
        PageResult<UserVO> page = createLambdaQuery().page(pageNumber, pageSize, UserVO.class);
        for (UserVO userVO : page.getList()) {
            userVO.setRoles(selectRole(userVO.getId()));
        }
        return page;
    }

}
