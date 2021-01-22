package net.hserver.hp.server.service.impl;

import net.hserver.hp.server.dao.AppDao;
import net.hserver.hp.server.domian.entity.AppEntity;
import net.hserver.hp.server.service.AppService;
import net.hserver.hp.server.utils.DateUtil;
import org.beetl.sql.core.page.PageResult;
import top.hserver.core.ioc.annotation.Autowired;
import top.hserver.core.ioc.annotation.Bean;

import java.util.UUID;

@Bean
public class AppServiceImpl implements AppService {

    @Autowired
    private AppDao appDao;

    @Override
    public PageResult<AppEntity> list(Integer page, Integer pageSize) {
        PageResult<AppEntity> create_time_desc = appDao.createLambdaQuery().orderBy("create_time desc").page(page, pageSize);
        for (AppEntity appEntity : create_time_desc.getList()) {
            appEntity.setCreateTime(DateUtil.stampToDate(appEntity.getCreateTime()));
        }
        return create_time_desc;
    }

    @Override
    public boolean add(AppEntity appEntity) {
        appEntity.setId(UUID.randomUUID().toString());
        appEntity.setCreateTime(String.valueOf(System.currentTimeMillis()));
        appDao.insert(appEntity);
        return false;
    }

    @Override
    public void remove(String id) {
        appDao.deleteById(id);
    }

    @Override
    public AppEntity getAppVersion() {
        return appDao.createLambdaQuery().orderBy("create_time desc").singleSimple();
    }
}
