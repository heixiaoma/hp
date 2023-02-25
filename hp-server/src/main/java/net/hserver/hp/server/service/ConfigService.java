package net.hserver.hp.server.service;

import net.hserver.hp.server.domian.entity.ConfigEntity;
import net.hserver.hp.server.domian.entity.StatisticsEntity;
import org.beetl.sql.core.page.PageResult;

import java.util.List;

public interface ConfigService {

    boolean save(ConfigEntity config);

    List<ConfigEntity> list(String userId);
    List<ConfigEntity> listDevice(String deviceId);

    PageResult<ConfigEntity> list(Integer page, Integer pageSize);

    boolean remove(String id);
}
