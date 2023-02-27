package net.hserver.hp.server.service.impl;

import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.core.ioc.annotation.Bean;
import net.hserver.hp.server.dao.DomainDao;
import net.hserver.hp.server.domian.bean.DomainVo;
import net.hserver.hp.server.domian.entity.DomainEntity;
import net.hserver.hp.server.domian.entity.UserEntity;
import net.hserver.hp.server.service.DomainService;
import net.hserver.hp.server.service.UserService;
import net.hserver.hp.server.utils.DateUtil;
import org.beetl.sql.core.page.DefaultPageResult;
import org.beetl.sql.core.page.PageResult;

import java.util.UUID;

@Bean
public class DomainServiceImpl implements DomainService {

    @Autowired
    private UserService userService;

    @Autowired
    private DomainDao domainDao;

    @Override
    public PageResult<DomainVo> list(Integer page, Integer pageSize, String username) {
        if (username!=null&&username.trim().length()>0){
            UserEntity user = userService.getUser(username);
            if (user==null){
                return new DefaultPageResult<>();
            }
            PageResult<DomainVo> create_time_desc = domainDao.createLambdaQuery().andEq(DomainEntity::getUserId,user.getId()).orderBy("create_time desc").page(page, pageSize).convert(
                    k->{
                        DomainVo domainVo = new DomainVo();
                        domainVo.setUsername(username);
                        domainVo.setDomain(k.getDomain());
                        domainVo.setCustomDomain(k.getCustomDomain());
                        domainVo.setCreateTime(k.getCreateTime());
                        domainVo.setUserId(k.getUserId());
                        domainVo.setId(k.getId());
                        return domainVo;
                    }
            );
            for (DomainVo appEntity : create_time_desc.getList()) {
                appEntity.setCreateTime(DateUtil.stampToDate(appEntity.getCreateTime()));
            }
            return create_time_desc;
        }else {
            PageResult<DomainVo> create_time_desc = domainDao.createLambdaQuery().orderBy("create_time desc").page(page, pageSize).convert(
                    k->{
                        DomainVo domainVo = new DomainVo();
                        UserEntity user = userService.getUserById(k.getUserId());
                        if (user!=null) {
                            domainVo.setUsername(user.getUsername());
                        }else {
                            domainVo.setUsername("用户不存在");
                        }
                        domainVo.setDomain(k.getDomain());
                        domainVo.setCustomDomain(k.getCustomDomain());
                        domainVo.setCreateTime(k.getCreateTime());
                        domainVo.setUserId(k.getUserId());
                        domainVo.setId(k.getId());
                        return domainVo;
                    }
            );
            for (DomainVo appEntity : create_time_desc.getList()) {
                appEntity.setCreateTime(DateUtil.stampToDate(appEntity.getCreateTime()));
            }
            return create_time_desc;

        }
    }

    @Override
    public boolean add(DomainVo k) {
        UserEntity user = userService.getUser(k.getUsername());
        if (user==null){
            return false;
        }
        DomainEntity domainEntity = new DomainEntity();
        domainEntity.setDomain(k.getDomain());
        domainEntity.setCustomDomain(k.getCustomDomain());
        domainEntity.setUserId(user.getId());
        domainEntity.setId(UUID.randomUUID().toString());
        domainEntity.setCreateTime(String.valueOf(System.currentTimeMillis()));
        domainDao.insert(domainEntity);
        return true;
    }

    @Override
    public void remove(String id) {
        domainDao.deleteById(id);
    }
}
