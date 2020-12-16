package com.system.service;

import com.system.dao.RolePermissionDao;
import top.hserver.core.ioc.annotation.Autowired;
import top.hserver.core.ioc.annotation.Bean;

/**
 * @author hxm
 */
@Bean
public class RolePermisssionService {

    @Autowired
    private RolePermissionDao rolePermissionDao;
}
