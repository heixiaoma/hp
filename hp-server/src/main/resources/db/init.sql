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
  "level" Integer,
  "login_ip" TEXT,
  "login_time" TEXT,
  "create_time" TEXT,
  PRIMARY KEY ("id")
);

DROP TABLE IF EXISTS "sys_pay";
CREATE TABLE "sys_pay" (
                            "id" text NOT NULL,
                            "username" TEXT,
                            "price" TEXT,
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

DROP TABLE IF EXISTS "sys_domain";
CREATE TABLE "sys_domain" (
    "id" text NOT NULL,
    "user_id" text NOT NULL,
    "domain" TEXT,
    "create_time" TEXT,
    PRIMARY KEY ("id")
);

DROP TABLE IF EXISTS "sys_app";
CREATE TABLE "sys_app" (
  "id" text NOT NULL,
  "version_code" text NOT NULL,
  "update_content" text NOT NULL,
  "create_time" TEXT,
  PRIMARY KEY ("id")
);

DROP TABLE IF EXISTS "sys_statistics";
CREATE TABLE "sys_statistics" (
  "id" text NOT NULL,
  "username" text NOT NULL,
  "receive" text NOT NULL,
  "send" text NOT NULL,
  "connect_num" text NOT NULL,
  "pack_num" text NOT NULL,
  "port" Integer NOT NULL,
  "create_time" TEXT,
  PRIMARY KEY ("id")
);


DROP TABLE IF EXISTS "sys_config";
CREATE TABLE "sys_config" (
                              "id" text NOT NULL,
                              "user_id" text NOT NULL,
                              "username" text NOT NULL,
                              "password" text NOT NULL,
                              "device_id" text NOT NULL,
                              "user_host" TEXT,
                              "server_host" TEXT,
                              "type" TEXT,
                              "domain" TEXT,
                              "port" TEXT,
                              "create_time" TEXT,
                              PRIMARY KEY ("id")
);


# --添加管理员信息
INSERT INTO "sys_user"("id", "username", "password", "type","create_time") VALUES ('1', 'admin', '123456', '1','1609660694000');

INSERT INTO "sys_user"("id", "username", "password", "type","create_time") VALUES ('2', 'heixiaoma', '123456', '2','1609660694000');
INSERT INTO "sys_user"("id", "username", "password", "type","create_time") VALUES ('3', 'jishunan', '123456', '2','1609660694000');

INSERT INTO "sys_port"("id", "user_id", "port","create_time") VALUES ('1', '2', '8888','1609660694000');
INSERT INTO "sys_port"("id", "user_id", "port","create_time") VALUES ('2', '3', '9999','1609660694000');
INSERT INTO "sys_port"("id", "user_id", "port","create_time") VALUES ('3', '3', '10000','1609660694000');
INSERT INTO "sys_port"("id", "user_id", "port","create_time") VALUES ('4', '3', '12000','1609660694000');


PRAGMA foreign_keys = true;
