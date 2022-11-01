package net.hserver.hp.server.service.impl;

import net.hserver.hp.server.dao.DomainDao;
import net.hserver.hp.server.dao.PortDao;
import net.hserver.hp.server.dao.UserDao;
import net.hserver.hp.server.domian.entity.DomainEntity;
import net.hserver.hp.server.domian.entity.PortEntity;
import net.hserver.hp.server.domian.entity.UserEntity;
import net.hserver.hp.server.domian.vo.UserVo;
import net.hserver.hp.server.service.UserService;
import net.hserver.hp.server.utils.DateUtil;
import org.beetl.sql.core.SQLReady;
import org.beetl.sql.core.page.PageResult;
import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.core.ioc.annotation.Bean;
import org.beetl.sql.core.query.LambdaQuery;

import java.util.*;

/**
 * @author hxm
 */
@Bean
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PortDao portDao;
    @Autowired
    private DomainDao domainDao;


    @Override
    public List<PortEntity> getPort(String userId) {
        return portDao.createLambdaQuery().andEq(PortEntity::getUserId, userId).select();
    }

    @Override
    public List<DomainEntity> getDomain(String userId) {
        return domainDao.createLambdaQuery().andEq(DomainEntity::getUserId, userId).select();
    }


    @Override
    public UserEntity getUser(String username) {
        List<UserEntity> select = userDao.createLambdaQuery().andEq(UserEntity::getUsername, username.trim()).select();
        if (select != null && select.size() == 1) {
            return select.get(0);
        } else {
            return null;
        }
    }

    @Override
    public UserVo domainLogin(String domain, String password, String address) {
        List<DomainEntity> select1 = domainDao.createLambdaQuery().andEq(DomainEntity::getDomain, domain).select();
        if (select1 == null||select1.isEmpty()) {
            return null;
        }
        DomainEntity domainEntity1 = select1.get(0);
        String userId = domainEntity1.getUserId();
        UserEntity user = userDao.single(userId);
        if (user.getPassword().equals(password)) {
            List<PortEntity> select = getPort(user.getId());
            List<DomainEntity> domainEntityList = getDomain(user.getId());
            UserVo userVo = new UserVo();
            userVo.setId(user.getId());
            userVo.setUsername(user.getUsername());
            userVo.setPassword(user.getPassword());
            userVo.setType(user.getType());
            List<Integer> ports = new ArrayList<>();
            for (PortEntity portEntity : select) {
                ports.add(portEntity.getPort());
            }
            List<String> domains = new ArrayList<>();
            for (DomainEntity domainEntity : domainEntityList) {
                domains.add(domainEntity.getDomain());
            }
            userVo.setLevel(user.getLevel());
            userVo.setDomains(domains);
            userVo.setPorts(ports);
            updateLogin(user.getUsername(), address);
            return userVo;
        }
        return null;
    }

    @Override
    public UserVo login(String username, String password, String address) {
        UserEntity user = getUser(username);
        if (user == null) {
            return null;
        }
        if (user.getPassword().equals(password)) {
            List<PortEntity> select = getPort(user.getId());
            List<DomainEntity> domain = getDomain(user.getId());
            UserVo userVo = new UserVo();
            userVo.setId(user.getId());
            userVo.setUsername(username);
            userVo.setPassword(password);
            userVo.setType(user.getType());
            List<Integer> ports = new ArrayList<>();
            for (PortEntity portEntity : select) {
                ports.add(portEntity.getPort());
            }
            List<String> domains = new ArrayList<>();
            for (DomainEntity domainEntity : domain) {
                domains.add(domainEntity.getDomain());
            }
            userVo.setLevel(user.getLevel());
            userVo.setDomains(domains);
            userVo.setPorts(ports);
            updateLogin(username, address);
            return userVo;
        }
        return null;
    }

    @Override
    public PageResult<UserVo> list(Integer page, Integer pageSize, String username) {
        LambdaQuery<UserEntity> lambdaQuery = userDao.createLambdaQuery();
        if (username != null && username.trim().length() > 0) {
            lambdaQuery.andLike(UserEntity::getUsername, "%" + username + "%");
        }
        PageResult<UserVo> page1 = lambdaQuery.orderBy("create_time desc").page(page, pageSize, UserVo.class);
        List<UserVo> list = page1.getList();
        for (UserVo userVo : list) {
            userVo.setLoginIp(userVo.getLoginIp() == null ? "" : userVo.getLoginIp());
            userVo.setCreateTime(DateUtil.stampToDate(userVo.getCreateTime()));
            userVo.setLoginTime(DateUtil.stampToDate(userVo.getLoginTime()));
            List<PortEntity> select = getPort(userVo.getId());
            List<DomainEntity> domain = getDomain(userVo.getId());
            List<Integer> ports = new ArrayList<>();
            for (PortEntity portEntity : select) {
                ports.add(portEntity.getPort());
            }
            List<String> domains = new ArrayList<>();
            for (DomainEntity domainEntity : domain) {
                domains.add(domainEntity.getDomain());
            }
            userVo.setDomains(domains);
            userVo.setPorts(ports);
        }
        return page1;
    }

    @Override
    public void updateLogin(String username, String ip) {
        userDao.updateLogin(String.valueOf(System.currentTimeMillis()), ip, username);
    }

    @Override
    public void editUser(String username, String password, String ports, Integer type, Integer level,String domains) {
        UserEntity user = getUser(username);
        if (user != null) {
            if (type != null) {
                user.setType(type);
                if (type == -1) {
                    //强制下线操作
                }
            }
            if (level != null) {
                user.setLevel(level);
            }
            user.setPassword(password);
            userDao.updateById(user);
        }
        assert user != null;
        List<PortEntity> port = getPort(user.getId());
        for (PortEntity portEntity : port) {
            portDao.deleteById(portEntity.getId());
        }
        // 修复ports为空情况
        if (!ports.equals("")) {
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

        if (domains != null) {
            String[] split = domains.split(",");
            if (split.length > 0) {
                domainDao.deleteByUserId(user.getId());
                for (String s : split) {
                    DomainEntity domainEntity = new DomainEntity();
                    domainEntity.setId(UUID.randomUUID().toString());
                    domainEntity.setUserId(user.getId());
                    domainEntity.setDomain(s.trim());
                    domainDao.insert(domainEntity);
                }
            }
        }
    }

    @Override
    public boolean addUser(String username, String password, String ports, String domains, Integer level) {
        UserEntity user = getUser(username);
        if (user != null) {
            return false;
        }
        user = new UserEntity();
        user.setPassword(password.trim());
        user.setCreateTime(String.valueOf(System.currentTimeMillis()));
        user.setType(2);
        user.setId(UUID.randomUUID().toString());
        user.setUsername(username.trim());
        if (level == null) {
            user.setLevel(0);
        } else {
            user.setLevel(level);
        }
        userDao.insert(user);
        if (ports != null) {
            String[] split = ports.split(",");
            if (split.length > 0) {
                for (String s : split) {
                    if (s.trim().length()>0) {
                        PortEntity portEntity = new PortEntity();
                        portEntity.setId(UUID.randomUUID().toString());
                        portEntity.setUserId(user.getId());
                        portEntity.setPort(Integer.parseInt(s));
                        portDao.insert(portEntity);
                    }
                }
            }
        }

        if (domains != null) {
            String[] split = domains.split(",");
            if (split.length > 0) {
                for (String s : split) {
                    DomainEntity domainEntity = new DomainEntity();
                    domainEntity.setId(UUID.randomUUID().toString());
                    domainEntity.setUserId(user.getId());
                    domainEntity.setDomain(s.trim());
                    domainDao.insert(domainEntity);
                }
            }
        }
        return true;
    }

    @Override
    public void remove(String username) {
        UserEntity user = getUser(username);
        if (user == null) {
            return;
        }
        List<PortEntity> port = getPort(user.getId());
        for (PortEntity portEntity : port) {
            portDao.deleteById(portEntity.getId());
        }
        userDao.deleteById(user.getId());
    }

    @Override
    public void removeExp() {
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.MONTH, -1);
        userDao.getSQLManager().executeUpdate(
                new SQLReady("delete from sys_user where (level is null or level = 0) and login_time < " + instance.getTimeInMillis())
        );
    }
}
