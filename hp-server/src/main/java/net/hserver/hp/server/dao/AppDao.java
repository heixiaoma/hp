package net.hserver.hp.server.dao;

import cn.hserver.plugin.mybatis.annotation.Mybatis;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.hserver.hp.server.domian.entity.AppEntity;

/**
 * @author hxm
 */
@Mybatis
public interface AppDao extends BaseMapper<AppEntity> {

}
