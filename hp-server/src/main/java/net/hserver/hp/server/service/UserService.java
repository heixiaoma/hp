package net.hserver.hp.server.service;

import net.hserver.hp.server.domian.entity.DomainEntity;
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
    UserVo domainLogin(String username, String password,String domain, String address);
    UserVo login(String username, String password, String address);

    /**
     * 获取用户对象
     *
     * @param username
     * @return
     */
    UserEntity getUser(String username);

    /**
     * 获取用户的端口
     *
     * @param userId
     * @return
     */
    List<PortEntity> getPort(String userId);

    UserEntity getUserById(String userId);

    List<DomainEntity> getDomain(String userId);

    /**
     * 列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    PageResult<UserVo> list(Integer page, Integer pageSize, String username);

    /**
     * 编辑用户
     *
     * @param username
     * @param password
     * @param ports
     */
    void editUser(String username, String password, String ports, Integer type, Integer level,String domains);

    /**
     * 更新登录时间
     *
     * @param username
     */
    void updateLogin(String username, String ip);

    /**
     * 添加用户
     *
     * @param username
     * @param password
     * @param ports
     */
    boolean addUser(String username, String password, String ports, String domains,Integer level);

    /**
     * 删除用户
     *
     * @param username
     */
    void remove(String username);

    /**
     * 移除过期用户
     */
    void removeExp();

}
