package com.system.service;

import com.system.dao.ModuleDao;
import com.system.domain.entity.ModuleEntity;
import com.system.domain.vo.ModuleVO;
import top.hserver.core.interfaces.PermissionAdapter;
import top.hserver.core.ioc.annotation.Autowired;
import top.hserver.core.ioc.annotation.Bean;
import top.hserver.core.ioc.annotation.RequiresPermissions;
import top.hserver.core.server.router.RouterPermission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hxm
 */
@Bean
public class ModuleService {

    @Autowired
    private ModuleDao moduleDao;

    public void sync() {
        moduleDao.deleteAll();
        List<RouterPermission> routerPermissions = PermissionAdapter.getRouterPermissions();
        for (RouterPermission routerPermission : routerPermissions) {
            String name;
            if (routerPermission.getControllerName().trim().equals("")) {
                name = routerPermission.getControllerPackageName();
            } else {
                name = routerPermission.getControllerName();
            }
            RequiresPermissions requiresPermissions = routerPermission.getRequiresPermissions();
            String[] value = requiresPermissions.value();
            for (String s : value) {
                ModuleEntity moduleEntity = new ModuleEntity();
                moduleEntity.setName(name);
                moduleEntity.setPermission(s);
                moduleDao.insert(moduleEntity);
            }
        }
    }

    public List<ModuleEntity> list() {
        return moduleDao.createLambdaQuery().orderBy(ModuleEntity::getName).select();
    }

    public List<ModuleVO> getModuleList() {
        List<ModuleEntity> select = moduleDao.createLambdaQuery().orderBy(ModuleEntity::getName).select();
        Map<String, ModuleVO> moduleVOS = new HashMap<>();
        int i = 1;
        for (ModuleEntity moduleEntity : select) {
            ModuleVO moduleVO = moduleVOS.get(moduleEntity.getName());
            if (moduleVO == null) {
                moduleVO = new ModuleVO();
                moduleVO.setTitle(moduleEntity.getName());
                moduleVO.setChildren(new ArrayList<>());
                moduleVOS.put(moduleEntity.getName(), moduleVO);
            }
            ModuleVO moduleVO1 = new ModuleVO();
            moduleVO1.setPermission(moduleEntity.getPermission());
            moduleVO1.setTitle(moduleEntity.getPermission());
            moduleVO.getChildren().add(moduleVO1);
        }
        return new ArrayList(moduleVOS.values());
    }

}
