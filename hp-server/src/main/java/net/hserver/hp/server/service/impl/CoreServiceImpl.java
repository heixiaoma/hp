package net.hserver.hp.server.service.impl;

import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.core.ioc.annotation.Bean;
import net.hserver.hp.server.dao.AppDao;
import net.hserver.hp.server.dao.CoreDao;
import net.hserver.hp.server.domian.entity.AppEntity;
import net.hserver.hp.server.domian.entity.CoreEntity;
import net.hserver.hp.server.service.AppService;
import net.hserver.hp.server.service.CoreService;
import net.hserver.hp.server.utils.DateUtil;
import org.beetl.sql.core.page.PageResult;

import java.util.UUID;

@Bean
public class CoreServiceImpl implements CoreService {

    @Autowired
    private CoreDao coreDao;

    @Override
    public PageResult<CoreEntity> list(Integer page, Integer pageSize) {
        PageResult<CoreEntity> create_time_desc = coreDao.createLambdaQuery().orderBy("create_time desc").page(page, pageSize);
        for (CoreEntity coreEntity : create_time_desc.getList()) {
            coreEntity.setCreateTime(DateUtil.stampToDate(coreEntity.getCreateTime()));
        }
        return create_time_desc;
    }

    @Override
    public boolean add(CoreEntity coreEntity) {
        coreEntity.setId(UUID.randomUUID().toString());
        coreEntity.setCreateTime(String.valueOf(System.currentTimeMillis()));
        coreDao.insert(coreEntity);
        return false;
    }

    @Override
    public void remove(String id) {
        coreDao.deleteById(id);
    }

    @Override
    public CoreEntity getAppVersion() {
        return coreDao.createLambdaQuery().orderBy("create_time desc").singleSimple();
    }
}
