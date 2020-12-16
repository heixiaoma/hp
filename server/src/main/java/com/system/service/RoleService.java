package com.system.service;

import com.system.dao.RoleDao;
import com.system.dao.RolePermissionDao;
import com.system.domain.entity.RoleEntity;
import com.system.domain.entity.RolePermissionEntity;
import com.system.domain.dto.MpDTO;
import top.hserver.core.ioc.annotation.Autowired;
import top.hserver.core.ioc.annotation.Bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author hxm
 */
@Bean
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    public List<RoleEntity> list() {
        return roleDao.createLambdaQuery().select();
    }

    public void add(RoleEntity roleEntity) {
        roleDao.insert(roleEntity);
    }

    public void updateRole(RoleEntity roleEntity) {
        roleDao.updateById(roleEntity);
    }

    public void deleteRole(Integer id) {
        roleDao.deleteById(id);
    }

    public List<Integer> getMenuPermission(Integer roleId) {
        List<Integer> ids = new ArrayList<>();
        List<RolePermissionEntity> select = rolePermissionDao.createLambdaQuery()
                .andEq(RolePermissionEntity::getType, 0)
                .andEq(RolePermissionEntity::getRoleId, roleId)
                .select();
        for (RolePermissionEntity rolePermissionEntity : select) {
            ids.add(rolePermissionEntity.getMId());
        }
        return ids;
    }

    public List<String> getModulePermission(Integer roleId) {
        List<String> ids = new ArrayList<>();
        List<RolePermissionEntity> select = rolePermissionDao.createLambdaQuery()
                .andEq(RolePermissionEntity::getType, 1)
                .andEq(RolePermissionEntity::getRoleId, roleId)
                .select();
        for (RolePermissionEntity rolePermissionEntity : select) {
            ids.add(rolePermissionEntity.getPermission());
        }
        return ids;
    }

    public void updateMenuPermission(MpDTO mpDTO) {
        rolePermissionDao.deleteMenuPermission(mpDTO.getRoleId());
        for (String id : mpDTO.getIds()) {
            RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
            rolePermissionEntity.setMId(Integer.parseInt(id));
            rolePermissionEntity.setRoleId(mpDTO.getRoleId());
            rolePermissionEntity.setType(0);
            rolePermissionDao.insert(rolePermissionEntity);
        }
    }

    public void updateModulePermission(MpDTO mpDTO) {
        rolePermissionDao.updateModulePermission(mpDTO.getRoleId());
        for (String id : mpDTO.getIds()) {
            RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
            rolePermissionEntity.setPermission(id);
            rolePermissionEntity.setRoleId(mpDTO.getRoleId());
            rolePermissionEntity.setType(1);
            rolePermissionDao.insert(rolePermissionEntity);
        }
    }


    public Map<Integer, List<String>> getRolePermission() {
        List<RolePermissionEntity> rolePermissionEntities = rolePermissionDao.createLambdaQuery()
                .andEq(RolePermissionEntity::getType, 1)
                .select();
        Map<Integer, List<String>> map = new HashMap<>(2);
        for (RolePermissionEntity rolePermissionEntity : rolePermissionEntities) {
            if (map.containsKey(rolePermissionEntity.getRoleId())) {
                map.get(rolePermissionEntity.getRoleId()).add(rolePermissionEntity.getPermission());
            } else {
                List<String> array = new ArrayList<>();
                array.add(rolePermissionEntity.getPermission());
                map.put(rolePermissionEntity.getRoleId(), array);
            }
        }
        return map;
    }

}
