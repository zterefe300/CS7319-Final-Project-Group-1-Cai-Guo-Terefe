/*
 Navicat MySQL Dump SQL

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80040 (8.0.40)
 Source Host           : localhost:3306
 Source Schema         : inventory

 Target Server Type    : MySQL
 Target Server Version : 80040 (8.0.40)
 File Encoding         : 65001

 Date: 22/10/2024 22:40:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `storkRecord`;
DROP TABLE IF EXISTS `employee`;


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `user_name` varchar(64) NOT NULL COMMENT 'employee login name',
                        `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                        `first_name` varchar(64) DEFAULT NULL,
                        `last_name` varchar(64) DEFAULT NULL,
                        `role_type` tinyint(1) DEFAULT NULL COMMENT '0-admin ;1-employee',
                        `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                        `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='employee';

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `name` varchar(64) NOT NULL COMMENT 'item name',
                        `detail` varchar(255) DEFAULT NULL COMMENT 'detail',
                        `pics` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'pics, e.g.[xx,xx,xxx]',
                        `qty` int NOT NULL DEFAULT '0' COMMENT 'total quantity',
                        `qty_sold` int NOT NULL DEFAULT '0' COMMENT 'sold quantity',
                        `threshold` int DEFAULT '0' COMMENT 'minimum threshold for alert',
                        `vendor_id` int DEFAULT NULL COMMENT 'the id of table vendor’',
                        `sort` int DEFAULT '0' COMMENT 'the order for display',
                        `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                        `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='item';

-- ----------------------------
-- Table structure for storkRecord
-- ----------------------------
DROP TABLE IF EXISTS `stock_record`;
CREATE TABLE `stock_record` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `item_id` int NOT NULL COMMENT 'the id of table item',
                                `quantity` int NOT NULL COMMENT 'updated quantity',
                                `opt_type` tinyint(1) DEFAULT NULL COMMENT '0-deduct stock;1-add stock',
                                `operator` varchar(64) DEFAULT NULL COMMENT 'operator name',
                                `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'the operation time',
                                `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='StockChangeRecord';

-- ----------------------------
-- Table structure for vendor
-- ----------------------------
DROP TABLE IF EXISTS `vendor`;
CREATE TABLE `vendor` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'name of the vendor',
                          `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'email of the vendor',
                          `phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'phone of the vendor',
                          `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                          `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='vendor';

SET FOREIGN_KEY_CHECKS = 1;
