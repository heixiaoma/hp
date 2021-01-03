package net.hserver.hp.server.service;

import net.hserver.hp.server.domian.entity.PortEntity;
import net.hserver.hp.server.domian.entity.UserEntity;
import net.hserver.hp.server.domian.vo.UserVo;
import org.beetl.sql.core.page.PageResult;

import java.util.List;

/**
 * @author hxm
 */
public interface UserService {

    /**
     * 登录获取用户信息
     *
     * @param username
     * @param password
     * @return
     */
    UserVo login(String username, String password);

    /**
     * 获取用户对象
     *
     * @param username
     * @return
     */
    UserEntity getUser(String username);

    /**
     * 获取用户的端口
     * @param userId
     * @return
     */
    List<PortEntity> getPort(String userId);

    /**
     * 列表
     * @param page
     * @param pageSize
     * @return
     */
    PageResult<UserVo> list(Integer page, Integer pageSize);


}
