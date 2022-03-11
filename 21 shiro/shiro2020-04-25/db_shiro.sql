/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : db_shiro

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 25/04/2020 22:58:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_module
-- ----------------------------
DROP TABLE IF EXISTS `sys_module`;
CREATE TABLE `sys_module`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '功能主键',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父级主键',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '编码',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '项目类型:1-菜单2-按钮3-链接4-表单',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'url地址',
  `is_delete` tinyint(1) NULL DEFAULT NULL COMMENT '删除标记:0未删除，1删除',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `create_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
  `update_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改用户',
  `ts` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '时间戳',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统功能表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_module
-- ----------------------------
INSERT INTO `sys_module` VALUES (1, 0, 'user', '用户管理', NULL, 1, '/user/index.html', 0, '2020-04-14 19:07:31', 'SYSTEM', '2020-04-14 19:07:33', 'SYSTEM', '2020-04-20 21:50:23');
INSERT INTO `sys_module` VALUES (2, 1, 'user:add1', '用户添加', NULL, 2, NULL, 0, NULL, NULL, NULL, NULL, '2020-04-25 21:12:47');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色主键',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '角色编码',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `is_delete` tinyint(1) NULL DEFAULT NULL COMMENT '删除标记:0未删除，1删除',
  `mark` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
  `update_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '修改用户',
  `ts` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '时间戳',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '000', '超级管理员', 0, '超级管理员', '2017-03-08 15:00:42', NULL, '2020-04-14 19:07:58', NULL, '2020-04-14 19:08:00');
INSERT INTO `sys_role` VALUES (2, '001', '测试角色', 0, '测试角色', '2018-12-09 17:48:13', NULL, '2018-12-09 17:48:26', NULL, '2020-04-13 22:36:17');

-- ----------------------------
-- Table structure for sys_role_module_ref
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_module_ref`;
CREATE TABLE `sys_role_module_ref`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户角色关联',
  `module_id` bigint(20) NULL DEFAULT NULL COMMENT '用户主键',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色主键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色功能关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_module_ref
-- ----------------------------
INSERT INTO `sys_role_module_ref` VALUES (1, 1, 1);
INSERT INTO `sys_role_module_ref` VALUES (2, 2, 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录账户',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录密码',
  `secretkey` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐值，密码秘钥',
  `locked` tinyint(1) NULL DEFAULT NULL,
  `real_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `is_delete` tinyint(1) NULL DEFAULT NULL COMMENT '删除标记:0未删除，1删除',
  `create_time` datetime(0) NULL DEFAULT '1970-01-01 00:00:00' COMMENT '创建日期',
  `create_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  `update_user` datetime(0) NULL DEFAULT NULL COMMENT '修改日期',
  `update_time` datetime(0) NULL DEFAULT '1970-01-01 00:00:00' COMMENT '修改用户',
  `ts` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '时间戳',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'system', '7794d9e38e986f74751ed6fa6f6075a4', 'system', 0, '超级管理员', 0, NULL, 'SYSTEM', '2018-12-13 19:19:36', '2018-12-13 16:03:49', '2020-04-14 18:28:12');
INSERT INTO `sys_user` VALUES (2, 'admin', '578966146da8c139355b7bddee09dd70', 'pFtIkWuE7UcKQw0tX1Z5', 0, '管理员1', 0, '2018-12-07 15:04:03', 'SYSTEM', '2018-12-13 16:03:49', '2018-12-13 16:03:49', '2020-04-14 18:28:13');

-- ----------------------------
-- Table structure for sys_user_role_ref
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_ref`;
CREATE TABLE `sys_user_role_ref`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户角色关联',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户主键',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色主键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role_ref
-- ----------------------------
INSERT INTO `sys_user_role_ref` VALUES (1, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
