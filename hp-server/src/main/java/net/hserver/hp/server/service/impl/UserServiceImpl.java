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
import java.util.UUID;

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

    @Override
    public void editUser(String username, String password, String ports) {
        UserEntity user = getUser(username);
        if (user != null) {
            user.setPassword(password);
            userDao.updateById(user);
        }
        assert user != null;
        List<PortEntity> port = getPort(user.getId());
        for (PortEntity portEntity : port) {
            portDao.deleteById(portEntity.getId());
        }

        String[] split = ports.split(",");
        if (split.length > 0) {
            for (String s : split) {
                PortEntity portEntity = new PortEntity();
                portEntity.setId(UUID.randomUUID().toString());
                portEntity.setUserId(user.getId());
                portEntity.setPort(Integer.parseInt(s));
                portDao.insert(portEntity);
            }
        }
    }

    @Override
    public boolean addUser(String username, String password, String ports) {
        UserEntity user = getUser(username);
        if (user != null) {
            return false;
        }
        user = new UserEntity();
        user.setPassword(password);
        user.setCreateTime(String.valueOf(System.currentTimeMillis()));
        user.setType(2);
        user.setId(UUID.randomUUID().toString());
        user.setUsername(username);
        userDao.insert(user);
        if (ports != null) {
            String[] split = ports.split(",");
            if (split.length > 0) {
                for (String s : split) {
                    PortEntity portEntity = new PortEntity();
                    portEntity.setId(UUID.randomUUID().toString());
                    portEntity.setUserId(user.getId());
                    portEntity.setPort(Integer.parseInt(s));
                    portDao.insert(portEntity);
                }
            }
        }
        return true;
    }

    @Override
    public void remove(String username) {
        UserEntity user = getUser(username);
        List<PortEntity> port = getPort(user.getId());
        for (PortEntity portEntity : port) {
            portDao.deleteById(portEntity.getId());
        }
        userDao.deleteById(user.getId());
    }
}
