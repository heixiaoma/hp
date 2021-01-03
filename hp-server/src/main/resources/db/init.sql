/*
 Navicat Premium Data Transfer

 Source Server         : docker
 Source Server Type    : SQLite
 Source Server Version : 3021000
 Source Schema         : main

 Target Server Type    : SQLite
 Target Server Version : 3021000
 File Encoding         : 65001

 Date: 10/12/2020 12:27:59
*/

PRAGMA foreign_keys = false;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS "sys_user";
CREATE TABLE "sys_user" (
  "id" text NOT NULL,
  "username" TEXT,
  "password" TEXT,
  "type" Integer,
  "create_time" TEXT,
  PRIMARY KEY ("id")
);

-- ----------------------------
-- Table structure for sys_port
-- ----------------------------
DROP TABLE IF EXISTS "sys_port";
CREATE TABLE "sys_port" (
  "id" text NOT NULL,
  "user_id" text NOT NULL,
  "port" Integer,
  "create_time" TEXT,
  PRIMARY KEY ("id")
);

--添加管理员信息
INSERT INTO "sys_user"("id", "username", "password", "type","create_time") VALUES ('1', 'admin', '123456', '1','1609660694000');

INSERT INTO "sys_user"("id", "username", "password", "type","create_time") VALUES ('2', 'heixiaoma', '123456', '2','1609660694000');
INSERT INTO "sys_user"("id", "username", "password", "type","create_time") VALUES ('3', 'jishunan', '123456', '2','1609660694000');

INSERT INTO "sys_port"("id", "user_id", "port","create_time") VALUES ('1', '2', '8888','1609660694000');
INSERT INTO "sys_port"("id", "user_id", "port","create_time") VALUES ('2', '3', '9999','1609660694000');
INSERT INTO "sys_port"("id", "user_id", "port","create_time") VALUES ('3', '3', '10000','1609660694000');


PRAGMA foreign_keys = true;