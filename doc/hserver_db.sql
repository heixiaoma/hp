/*
 Navicat Premium Data Transfer

 Source Server         : 5.7
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : hserver_db

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 30/09/2020 17:15:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `redirect` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `parent_id` int(11) NULL DEFAULT NULL,
  `sort` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (19, '/api', 'Layout', '', 'Api', 'api文档', '', NULL, 2);
INSERT INTO `sys_menu` VALUES (20, 'index', 'api/index', '', '系统文档', '系统文档', 'el-icon-s-order', 19, 1);
INSERT INTO `sys_menu` VALUES (18, 'http://baidu.com', 'Layout', '', 'baidu', '百度测试', 'el-icon-s-promotion', NULL, 3);
INSERT INTO `sys_menu` VALUES (13, '/system', 'Layout', '', 'System', '系统管理', 'el-icon-s-platform', NULL, 1);
INSERT INTO `sys_menu` VALUES (14, 'user', 'system/user', '', 'User', '用户管理', 'el-icon-user-solid', 13, 1);
INSERT INTO `sys_menu` VALUES (15, 'role', 'system/role', '', 'Role', '角色管理', 'peoples', 13, 2);
INSERT INTO `sys_menu` VALUES (16, 'menu', 'system/menu', '', 'Menu', '菜单管理', 'tree-table', 13, 3);
INSERT INTO `sys_menu` VALUES (17, 'module', 'system/module', '', 'Module', '模块管理', 'el-icon-s-management', 13, 4);

-- ----------------------------
-- Table structure for sys_module
-- ----------------------------
DROP TABLE IF EXISTS `sys_module`;
CREATE TABLE `sys_module`  (
  `permission` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`permission`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_module
-- ----------------------------
INSERT INTO `sys_module` VALUES ('get:/system/user/list', '用户管理');
INSERT INTO `sys_module` VALUES ('get:/system/module/list', '模块管理');
INSERT INTO `sys_module` VALUES ('get:/system/menu/menu', '菜单管理');
INSERT INTO `sys_module` VALUES ('get:/system/menu/list', '菜单管理');
INSERT INTO `sys_module` VALUES ('get:/system/role/getMenuPermission', '角色管理');
INSERT INTO `sys_module` VALUES ('get:/system/user/info', '用户管理');
INSERT INTO `sys_module` VALUES ('get:/system/menu/listTop', '菜单管理');
INSERT INTO `sys_module` VALUES ('get:/system/module/getModuleList', '模块管理');
INSERT INTO `sys_module` VALUES ('get:/system/menu/deleteMenu', '菜单管理');
INSERT INTO `sys_module` VALUES ('get:/system/module/sync', '模块管理');
INSERT INTO `sys_module` VALUES ('get:/system/user/delete', '用户管理');
INSERT INTO `sys_module` VALUES ('get:/system/role/deleteRole', '角色管理');
INSERT INTO `sys_module` VALUES ('get:/system/role/list', '角色管理');
INSERT INTO `sys_module` VALUES ('get:/system/role/getModulePermission', '角色管理');
INSERT INTO `sys_module` VALUES ('post:/system/role/updateModulePermission', '角色管理');
INSERT INTO `sys_module` VALUES ('post:/system/user/edit', '用户管理');
INSERT INTO `sys_module` VALUES ('post:/system/user/logout', '用户管理');
INSERT INTO `sys_module` VALUES ('post:/system/role/updateRole', '角色管理');
INSERT INTO `sys_module` VALUES ('post:/system/role/updateMenuPermission', '角色管理');
INSERT INTO `sys_module` VALUES ('post:/system/user/add', '用户管理');
INSERT INTO `sys_module` VALUES ('post:/system/menu/addMenu', '菜单管理');
INSERT INTO `sys_module` VALUES ('post:/system/role/add', '角色管理');
INSERT INTO `sys_module` VALUES ('post:/system/user/multiDelete', '用户管理');
INSERT INTO `sys_module` VALUES ('post:/system/menu/updateMenu', '菜单管理');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', '超级管理员');
INSERT INTO `sys_role` VALUES (2, '普通用户', '用户');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` bit(1) NULL DEFAULT NULL,
  `role_id` int(11) NULL DEFAULT NULL,
  `m_id` int(11) NULL DEFAULT NULL COMMENT '菜单ID',
  `permission` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块主键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 246 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (45, b'0', 11, 4, NULL);
INSERT INTO `sys_role_permission` VALUES (46, b'1', 11, NULL, 'get:/system/module/list');
INSERT INTO `sys_role_permission` VALUES (47, b'1', 11, NULL, 'get:/system/module/sync');
INSERT INTO `sys_role_permission` VALUES (199, b'0', 2, 27, NULL);
INSERT INTO `sys_role_permission` VALUES (200, b'0', 2, 20, NULL);
INSERT INTO `sys_role_permission` VALUES (210, b'0', 1, 13, NULL);
INSERT INTO `sys_role_permission` VALUES (211, b'0', 1, 14, NULL);
INSERT INTO `sys_role_permission` VALUES (212, b'0', 1, 15, NULL);
INSERT INTO `sys_role_permission` VALUES (213, b'0', 1, 16, NULL);
INSERT INTO `sys_role_permission` VALUES (214, b'0', 1, 17, NULL);
INSERT INTO `sys_role_permission` VALUES (215, b'0', 1, 19, NULL);
INSERT INTO `sys_role_permission` VALUES (216, b'0', 1, 22, NULL);
INSERT INTO `sys_role_permission` VALUES (217, b'0', 1, 26, NULL);
INSERT INTO `sys_role_permission` VALUES (218, b'0', 1, 25, NULL);
INSERT INTO `sys_role_permission` VALUES (219, b'0', 1, 27, NULL);
INSERT INTO `sys_role_permission` VALUES (220, b'0', 1, 20, NULL);
INSERT INTO `sys_role_permission` VALUES (221, b'0', 1, 18, NULL);
INSERT INTO `sys_role_permission` VALUES (222, b'1', 1, NULL, 'get:/system/user/list');
INSERT INTO `sys_role_permission` VALUES (223, b'1', 1, NULL, 'get:/system/user/info');
INSERT INTO `sys_role_permission` VALUES (224, b'1', 1, NULL, 'get:/system/user/delete');
INSERT INTO `sys_role_permission` VALUES (225, b'1', 1, NULL, 'post:/system/user/edit');
INSERT INTO `sys_role_permission` VALUES (226, b'1', 1, NULL, 'post:/system/user/logout');
INSERT INTO `sys_role_permission` VALUES (227, b'1', 1, NULL, 'post:/system/user/add');
INSERT INTO `sys_role_permission` VALUES (228, b'1', 1, NULL, 'post:/system/user/multiDelete');
INSERT INTO `sys_role_permission` VALUES (229, b'1', 1, NULL, 'get:/system/menu/menu');
INSERT INTO `sys_role_permission` VALUES (230, b'1', 1, NULL, 'get:/system/menu/list');
INSERT INTO `sys_role_permission` VALUES (231, b'1', 1, NULL, 'get:/system/menu/listTop');
INSERT INTO `sys_role_permission` VALUES (232, b'1', 1, NULL, 'get:/system/menu/deleteMenu');
INSERT INTO `sys_role_permission` VALUES (233, b'1', 1, NULL, 'post:/system/menu/addMenu');
INSERT INTO `sys_role_permission` VALUES (234, b'1', 1, NULL, 'post:/system/menu/updateMenu');
INSERT INTO `sys_role_permission` VALUES (235, b'1', 1, NULL, 'get:/system/role/getMenuPermission');
INSERT INTO `sys_role_permission` VALUES (236, b'1', 1, NULL, 'get:/system/role/deleteRole');
INSERT INTO `sys_role_permission` VALUES (237, b'1', 1, NULL, 'get:/system/role/list');
INSERT INTO `sys_role_permission` VALUES (238, b'1', 1, NULL, 'get:/system/role/getModulePermission');
INSERT INTO `sys_role_permission` VALUES (239, b'1', 1, NULL, 'post:/system/role/updateModulePermission');
INSERT INTO `sys_role_permission` VALUES (240, b'1', 1, NULL, 'post:/system/role/updateRole');
INSERT INTO `sys_role_permission` VALUES (241, b'1', 1, NULL, 'post:/system/role/updateMenuPermission');
INSERT INTO `sys_role_permission` VALUES (242, b'1', 1, NULL, 'post:/system/role/add');
INSERT INTO `sys_role_permission` VALUES (243, b'1', 1, NULL, 'get:/system/module/list');
INSERT INTO `sys_role_permission` VALUES (244, b'1', 1, NULL, 'get:/system/module/getModuleList');
INSERT INTO `sys_role_permission` VALUES (245, b'1', 1, NULL, 'get:/system/module/sync');

-- ----------------------------
-- Table structure for sys_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_token`;
CREATE TABLE `sys_token`  (
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`token`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_token
-- ----------------------------
INSERT INTO `sys_token` VALUES ('eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlSWRzIjpbMl0sImV4cCI6MTYwMTQ1NjQ4MiwidXNlcklkIjoyLCJ1c2VybmFtZSI6ImhlaXhpYW9tYSJ9.GkXJrqvJo79gGPiJeLt9nHf7pzt3ia0z4VoB13MCe1w', 2, '2020-09-30 08:56:23');
INSERT INTO `sys_token` VALUES ('eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlSWRzIjpbMSwyXSwiZXhwIjoxNjAxNDU1MDE1LCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW4ifQ.YVdy0nAy6T3S-JQV9W27pQfh4geHjdVaq2Y_o6GrETU', 1, NULL);
INSERT INTO `sys_token` VALUES ('eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlSWRzIjpbMSwyXSwiZXhwIjoxNjAxNDU1MDE3LCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW4ifQ.9jjjyayJIwOReZQGXxlCvcq_KYRkoZudlnnVnYFt7v8', 1, NULL);
INSERT INTO `sys_token` VALUES ('eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlSWRzIjpbMSwyXSwiZXhwIjoxNjAxNDU1MzMzLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW4ifQ.7eh3m9YZPBqk2rMf3akuV-eqpAoATZYlfcplYF6YC-8', 1, NULL);
INSERT INTO `sys_token` VALUES ('eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlSWRzIjpbMSwyXSwiZXhwIjoxNjAxNDU1NDk0LCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW4ifQ.oljrRL5_Nfq2_soO7NsJALjkk39TUWCPui5LmBIyp7U', 1, '2020-09-30 08:44:54');
INSERT INTO `sys_token` VALUES ('eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlSWRzIjpbMSwyXSwiZXhwIjoxNjAxNDU1NDk5LCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW4ifQ.ZPhMKDLgxVAwwUbupnj8K6ukMf4ULKGW7xgQhq3QC9o', 1, '2020-09-30 08:44:59');
INSERT INTO `sys_token` VALUES ('eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlSWRzIjpbMSwyXSwiZXhwIjoxNjAxNDU2MjI5LCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW4ifQ.EXsx97NpxcVQPgEi0MdaDUBrUhOApusKSSazy01Kwcs', 1, '2020-09-30 08:52:10');
INSERT INTO `sys_token` VALUES ('eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlSWRzIjpbMSwyXSwiZXhwIjoxNjAxNDU2MjM0LCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW4ifQ.8PCP7CThVBU3pYpzpJv6D71uMpzPyK4VHebhagnGzaI', 1, '2020-09-30 08:52:14');
INSERT INTO `sys_token` VALUES ('eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlSWRzIjpbMSwyXSwiZXhwIjoxNjAxNDU2MjM1LCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW4ifQ.-S1DMEb-ezerKQeAXkP_SncWBKxmSsjGhFDIsadTQlk', 1, '2020-09-30 08:52:16');
INSERT INTO `sys_token` VALUES ('eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlSWRzIjpbMSwyXSwiZXhwIjoxNjAxNDU2NDg4LCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW4ifQ.VDpQmi3GdJTxQTLdxo6YWBs0QxqirJXiF54EiCL0fas', 1, '2020-09-30 08:56:28');
INSERT INTO `sys_token` VALUES ('eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlSWRzIjpbMSwyXSwiZXhwIjoxNjAxNDU2ODIxLCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW4ifQ.hov7CXgyTw3NGGltbiX5f4FAxSYBw3tTsgugYVh8Q9Q', 1, '2020-09-30 09:02:02');
INSERT INTO `sys_token` VALUES ('eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlSWRzIjpbMSwyXSwiZXhwIjoxNjAxNDU3MjE2LCJ1c2VySWQiOjEsInVzZXJuYW1lIjoiYWRtaW4ifQ._Ibl1EhHI6i-XSkRZfk1xxOC9v2kAR0kotTkaBBRp2Q', 1, '2020-09-30 09:08:36');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `state` int(1) NULL DEFAULT 0,
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 's05bse6q2qlb9qblls96s592y55y556s', '黑小马', 'https://portrait.gitee.com/uploads/avatars/user/631/1893827_heixiaomas_admin_1578961913.png!avatar100', 0, NULL, '2020-09-29 09:41:18');
INSERT INTO `sys_user` VALUES (2, 'heixiaoma', 's05bse6q2qlb9qblls96s592y55y556s', 'heixiaoma', 'https://portrait.gitee.com/uploads/avatars/user/631/1893827_heixiaomas_admin_1578961913.png!avatar100', 0, NULL, '2020-09-30 08:39:58');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL,
  `role_id` int(11) NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (3, NULL, 1, NULL);
INSERT INTO `sys_user_role` VALUES (4, NULL, 1, NULL);
INSERT INTO `sys_user_role` VALUES (5, NULL, 1, NULL);
INSERT INTO `sys_user_role` VALUES (6, NULL, 1, NULL);
INSERT INTO `sys_user_role` VALUES (39, NULL, 1, NULL);
INSERT INTO `sys_user_role` VALUES (40, NULL, 2, NULL);
INSERT INTO `sys_user_role` VALUES (51, 1, 1, NULL);
INSERT INTO `sys_user_role` VALUES (52, 1, 2, NULL);
INSERT INTO `sys_user_role` VALUES (54, 2, 2, NULL);

SET FOREIGN_KEY_CHECKS = 1;
