/*
 Navicat Premium Data Transfer

 Source Server         : Cuno_u
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 27/11/2022 20:13:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bills
-- ----------------------------
DROP TABLE IF EXISTS `bills`;
CREATE TABLE `bills` (
  `billID` bigint NOT NULL AUTO_INCREMENT,
  `memberID` bigint NOT NULL,
  `time` datetime DEFAULT NULL,
  `discripe` varchar(255) DEFAULT NULL,
  `amount` decimal(6,2) DEFAULT NULL,
  PRIMARY KEY (`billID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bills
-- ----------------------------
BEGIN;
INSERT INTO `bills` VALUES (1, 14, '2022-11-27 17:20:46', NULL, 20.00);
INSERT INTO `bills` VALUES (2, 14, '2022-11-27 17:21:04', NULL, 20.00);
INSERT INTO `bills` VALUES (3, 14, '2022-11-27 17:23:23', NULL, 790.00);
INSERT INTO `bills` VALUES (4, 14, '2022-11-27 17:27:12', NULL, 50.00);
INSERT INTO `bills` VALUES (5, 14, '2022-11-27 17:27:44', NULL, 1.00);
COMMIT;

-- ----------------------------
-- Table structure for members
-- ----------------------------
DROP TABLE IF EXISTS `members`;
CREATE TABLE `members` (
  `memberID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `phone` varchar(16) NOT NULL,
  `name` varchar(32) NOT NULL,
  `sex` varchar(2) DEFAULT NULL,
  `joinDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `balance` decimal(6,2) DEFAULT NULL,
  `password` varchar(32) NOT NULL,
  PRIMARY KEY (`memberID`),
  UNIQUE KEY `memberID` (`memberID`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of members
-- ----------------------------
BEGIN;
INSERT INTO `members` VALUES (12, '13725414842', '李四', '男', '2022-09-09 20:09:50', 1900.00, '123');
INSERT INTO `members` VALUES (13, '13725414843', '王五', '女', '2022-09-09 20:09:50', 1800.00, '123');
INSERT INTO `members` VALUES (14, '13725414844', '赵六', '男', '2022-09-09 20:09:50', 1151.00, '123');
INSERT INTO `members` VALUES (15, '13725414845', '武七', '男', '2022-09-09 20:09:50', 1900.00, '123');
INSERT INTO `members` VALUES (16, '13725414857', '李六', '女', '2022-11-14 17:15:12', 1900.00, '123');
INSERT INTO `members` VALUES (30, '15322033900', '齐八', '男', '2022-11-25 13:02:14', NULL, '12345678');
COMMIT;

-- ----------------------------
-- Table structure for sessions
-- ----------------------------
DROP TABLE IF EXISTS `sessions`;
CREATE TABLE `sessions` (
  `sessionID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `memberID` bigint unsigned NOT NULL,
  `onLineAt` timestamp NOT NULL,
  `ipAddressPort` varchar(22) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `deletedAt` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`sessionID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sessions
-- ----------------------------
BEGIN;
INSERT INTO `sessions` VALUES (79, 30, '2022-11-25 13:21:44', '/127.0.0.1:58783', '2022-11-27 19:46:14');
INSERT INTO `sessions` VALUES (80, 12, '2022-11-25 14:47:44', '/127.0.0.1:58883', '2022-11-27 18:31:54');
INSERT INTO `sessions` VALUES (81, 15, '2022-11-25 14:48:30', '/127.0.0.1:58889', '2022-11-25 14:52:03');
INSERT INTO `sessions` VALUES (82, 12, '2022-11-27 16:13:06', '/127.0.0.1:53750', '2022-11-27 18:31:54');
INSERT INTO `sessions` VALUES (83, 16, '2022-11-27 16:15:14', '/127.0.0.1:53767', '2022-11-27 19:46:17');
INSERT INTO `sessions` VALUES (84, 12, '2022-11-27 18:31:00', '/127.0.0.1:55798', '2022-11-27 18:31:54');
INSERT INTO `sessions` VALUES (85, 16, '2022-11-27 18:31:34', '/127.0.0.1:55805', '2022-11-27 19:46:17');
INSERT INTO `sessions` VALUES (86, 14, '2022-11-27 19:42:59', '/127.0.0.1:56092', '2022-11-27 20:01:32');
INSERT INTO `sessions` VALUES (87, 16, '2022-11-27 19:43:57', '/127.0.0.1:56221', '2022-11-27 19:46:17');
INSERT INTO `sessions` VALUES (88, 30, '2022-11-27 19:44:25', '/127.0.0.1:56237', '2022-11-27 19:46:14');
INSERT INTO `sessions` VALUES (89, 16, '2022-11-27 19:45:54', '/127.0.0.1:56276', '2022-11-27 19:46:17');
INSERT INTO `sessions` VALUES (90, 14, '2022-11-27 19:56:16', '/127.0.0.1:56418', '2022-11-27 20:01:32');
INSERT INTO `sessions` VALUES (91, 30, '2022-11-27 19:56:40', '/127.0.0.1:56427', NULL);
INSERT INTO `sessions` VALUES (92, 14, '2022-11-27 20:01:22', '/127.0.0.1:56487', '2022-11-27 20:01:32');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
