package net.hserver.hp.server.service.impl;

import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.core.ioc.annotation.Bean;
import net.hserver.hp.server.dao.PayDao;
import net.hserver.hp.server.domian.entity.PayEntity;
import net.hserver.hp.server.service.PayService;
import net.hserver.hp.server.utils.DateUtil;
import org.beetl.sql.core.SQLReady;
import org.beetl.sql.core.page.PageResult;

import java.util.List;
import java.util.UUID;

@Bean
public class PayServiceImpl implements PayService {

    @Autowired
    private PayDao payDao;

    @Override
    public PageResult<PayEntity> list(Integer page, Integer pageSize) {
        PageResult<PayEntity> create_time_desc = payDao.createLambdaQuery().orderBy("create_time desc").page(page, pageSize);
        for (PayEntity appEntity : create_time_desc.getList()) {
            appEntity.setCreateTime(DateUtil.stampToDate(appEntity.getCreateTime()));
        }
        return create_time_desc;
    }

    @Override
    public boolean add(PayEntity appEntity) {
        appEntity.setId(UUID.randomUUID().toString());
        appEntity.setCreateTime(String.valueOf(System.currentTimeMillis()));
        payDao.insert(appEntity);
        return false;
    }

    @Override
    public double countPrice() {
        Double execute = payDao.getSQLManager().executeQueryOne(
                new SQLReady("select sum(price) from sys_pay"), Double.class
        );
        if (execute!=null){
            return execute;
        }
        return 0;
    }

    @Override
    public void remove(String id) {
        payDao.deleteById(id);
    }

    @Override
    public List<PayEntity> getTop52() {
        return payDao.createLambdaQuery().orderBy("create_time desc").limit(0, 52).select();
    }

}
