package net.hserver.hp.server.service.impl;

import net.hserver.hp.server.dao.PortDao;
import net.hserver.hp.server.dao.UserDao;
import net.hserver.hp.server.domian.entity.PortEntity;
import net.hserver.hp.server.domian.entity.UserEntity;
import net.hserver.hp.server.domian.vo.UserVo;
import net.hserver.hp.server.service.UserService;
import net.hserver.hp.server.utils.DateUtil;
import org.beetl.sql.core.page.PageResult;
import top.hserver.core.ioc.annotation.Autowired;
import top.hserver.core.ioc.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hxm
 */
@Bean
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PortDao portDao;


    @Override
    public List<PortEntity> getPort(String userId) {
        return portDao.createLambdaQuery().andEq(PortEntity::getUserId, userId).select();
    }

    @Override
    public UserEntity getUser(String username) {
        List<UserEntity> select = userDao.createLambdaQuery().andEq(UserEntity::getUsername, username).select();
        if (select != null && select.size() == 1) {
            return select.get(0);
        } else {
            return null;
        }
    }

    @Override
    public UserVo login(String username, String password) {
        UserEntity user = getUser(username);
        if (user == null) {
            return null;
        }
        if (user.getPassword().equals(password)) {
            List<PortEntity> select = getPort(user.getId());
            UserVo userVo = new UserVo();
            userVo.setId(user.getId());
            userVo.setUsername(username);
            userVo.setPassword(password);
            userVo.setType(user.getType());
            List<Integer> ports = new ArrayList<>();
            for (PortEntity portEntity : select) {
                ports.add(portEntity.getPort());
            }
            userVo.setPorts(ports);
            return userVo;
        }
        return null;
    }

    @Override
    public PageResult<UserVo> list(Integer page, Integer pageSize) {
        PageResult<UserVo> page1 = userDao.createLambdaQuery().page(page, pageSize, UserVo.class);
        List<UserVo> list = page1.getList();
        for (UserVo userVo : list) {
            userVo.setCreateTime(DateUtil.stampToDate(userVo.getCreateTime()));
            List<PortEntity> select = getPort(userVo.getId());
            List<Integer> ports = new ArrayList<>();
            for (PortEntity portEntity : select) {
                ports.add(portEntity.getPort());
            }
            userVo.setPorts(ports);
        }
        return page1;
    }
}
