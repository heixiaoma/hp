package com.system.service;

import com.system.dao.MenuDao;
import com.system.dao.RolePermissionDao;
import com.system.domain.entity.MenuEntity;
import com.system.domain.vo.MenuTopVO;
import com.system.domain.vo.MenuVO;
import com.system.domain.vo.Meta;
import top.hserver.core.ioc.annotation.Autowired;
import top.hserver.core.ioc.annotation.Bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author hxm
 */
@Bean
public class MenuService {

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    public void addMenu(MenuEntity menuEntity) {
        menuDao.insert(menuEntity);
    }

    public void updateMenu(MenuEntity menuEntity) {
        menuDao.updateById(menuEntity);
    }

    public void deleteMenu(Integer id) {
        menuDao.deleteById(id);
    }


    public List<MenuVO> list() {
        List<MenuEntity> menuEntities = menuDao.createLambdaQuery().andIsNull(MenuEntity::getParentId).orderBy(MenuEntity::getSort).select();
        List<MenuVO> menuVOS = new ArrayList<>();
        for (MenuEntity menuEntity : menuEntities) {
            MenuVO menuVO = new MenuVO();
            menuVO.setId(menuEntity.getId());
            menuVO.setParentId(menuEntity.getParentId());
            menuVO.setPath(menuEntity.getPath());
            menuVO.setComponent(menuEntity.getComponent());
            menuVO.setName(menuEntity.getName());
            menuVO.setTitle(menuEntity.getTitle());
            menuVO.setIcon(menuEntity.getIcon());
            menuVO.setSort(menuEntity.getSort());
            menuVO.setRedirect(menuEntity.getRedirect());
            menuVO.setMeta(new Meta(menuEntity.getTitle(), menuEntity.getIcon()));
            List<MenuEntity> menuEntities1 = menuDao.createLambdaQuery().andEq(MenuEntity::getParentId, menuEntity.getId()).orderBy(MenuEntity::getSort).select();
            for (MenuEntity entity : menuEntities1) {
                MenuVO menuVO1 = new MenuVO();
                menuVO1.setId(entity.getId());
                menuVO1.setParentId(entity.getParentId());
                menuVO1.setPath(entity.getPath());
                menuVO1.setComponent(entity.getComponent());
                menuVO1.setName(entity.getName());
                menuVO1.setTitle(entity.getTitle());
                menuVO1.setIcon(entity.getIcon());
                menuVO1.setSort(entity.getSort());
                menuVO1.setRedirect(entity.getRedirect());
                menuVO1.setMeta(new Meta(entity.getTitle(), entity.getIcon()));
                List<MenuVO> children = menuVO.getChildren();
                if (children == null) {
                    children = new ArrayList<>();
                    menuVO.setChildren(children);
                }
                children.add(menuVO1);
                List<MenuEntity> menuEntities2 = menuDao.createLambdaQuery().andEq(MenuEntity::getParentId, entity.getId()).orderBy(MenuEntity::getSort).select();
                for (MenuEntity menuEntity1 : menuEntities2) {
                    MenuVO menuVO2 = new MenuVO();
                    menuVO2.setParentId(menuEntity1.getParentId());
                    menuVO2.setId(menuEntity1.getId());
                    menuVO2.setPath(menuEntity1.getPath());
                    menuVO2.setComponent(menuEntity1.getComponent());
                    menuVO2.setName(menuEntity1.getName());
                    menuVO2.setTitle(menuEntity1.getTitle());
                    menuVO2.setSort(menuEntity1.getSort());
                    menuVO2.setIcon(menuEntity1.getIcon());
                    menuVO2.setRedirect(menuEntity1.getRedirect());
                    menuVO2.setMeta(new Meta(menuEntity1.getTitle(), menuEntity1.getIcon()));
                    List<MenuVO> children1 = menuVO1.getChildren();
                    if (children1 == null) {
                        children1 = new ArrayList<>();
                        menuVO1.setChildren(children1);
                    }
                    children1.add(menuVO2);
                }
            }
            menuVOS.add(menuVO);
        }
        return menuVOS;
    }

    public List<MenuVO> menu(Integer[] roleIds) {
        List<MenuEntity> execute = menuDao.findMenuByRoleIds(roleIds);
        Set<Integer> integers = new HashSet<>();
        for (MenuEntity menuEntity : execute) {
            integers.add(menuEntity.getId());
            if (menuEntity.getParentId() != null) {
                integers.add(menuEntity.getParentId());
            }
        }
        List<MenuVO> list = list();

        List<MenuVO> tempIndex = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            //第一层
            MenuVO menuVO = list.get(i);
            if (!integers.contains(menuVO.getId())) {
                tempIndex.add(menuVO);
            }
        }
        list.removeAll(tempIndex);
        tempIndex.clear();
        for (int i = 0; i < list.size(); i++) {
            //第二层
            MenuVO menuVO = list.get(i);
            List<MenuVO> children = menuVO.getChildren();
            if (children != null) {
                for (int i1 = 0; i1 < children.size(); i1++) {
                    MenuVO menuVO1 = children.get(i1);
                    if (!integers.contains(menuVO1.getId())) {
                        tempIndex.add(menuVO1);
                    }
                }
                children.removeAll(tempIndex);
            }
        }
        tempIndex.clear();
        for (int i = 0; i < list.size(); i++) {
            //第二层
            MenuVO menuVO = list.get(i);
            List<MenuVO> children = menuVO.getChildren();
            if (children != null) {
                for (int i1 = 0; i1 < children.size(); i1++) {
                    MenuVO menuVO1 = children.get(i1);
                    List<MenuVO> children1 = menuVO1.getChildren();
                    if (children1 != null) {
                        for (int i2 = 0; i2 < children1.size(); i2++) {
                            MenuVO menuVO2 = children1.get(i2);
                            if (!integers.contains(menuVO2.getId())) {
                                tempIndex.add(menuVO2);
                            }
                        }
                        children1.removeAll(tempIndex);
                    }
                }
            }
        }
        tempIndex.clear();
        return list;
    }


    public List<MenuTopVO> listTop() {
        List<MenuEntity> menuEntities = menuDao.createLambdaQuery().andIsNull(MenuEntity::getParentId).orderBy(MenuEntity::getSort).select();
        List<MenuTopVO> menuVOS = new ArrayList<>();
        for (MenuEntity menuEntity : menuEntities) {
            MenuTopVO menuTopVO = new MenuTopVO();
            menuTopVO.setValue(menuEntity.getId());
            menuTopVO.setLabel(menuEntity.getTitle());
            List<MenuEntity> menuEntities1 = menuDao.createLambdaQuery().andEq(MenuEntity::getParentId, menuEntity.getId()).orderBy(MenuEntity::getSort).select();
            for (MenuEntity entity : menuEntities1) {
                MenuTopVO menuVO1 = new MenuTopVO();
                menuVO1.setLabel(entity.getTitle());
                menuVO1.setValue(entity.getId());
                List<MenuTopVO> children = menuTopVO.getChildren();
                if (children == null) {
                    children = new ArrayList<>();
                    menuTopVO.setChildren(children);
                }
                children.add(menuVO1);
            }
            menuVOS.add(menuTopVO);
        }
        return menuVOS;
    }

}
