-- 数据库：takeout，一键导入脚本

CREATE DATABASE IF NOT EXISTS `takeout` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `takeout`;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 订单表
CREATE TABLE IF NOT EXISTS `orders` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(64) NOT NULL,
  `status` INT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `address_book_id` BIGINT DEFAULT NULL,
  `order_time` DATETIME DEFAULT NULL,
  `checkout_time` DATETIME DEFAULT NULL,
  `pay_method` INT DEFAULT NULL,
  `pay_status` INT DEFAULT 0,
  `amount` DECIMAL(10,2) DEFAULT 0.00,
  `remark` VARCHAR(255) DEFAULT NULL,
  `user_name` VARCHAR(64) DEFAULT NULL,
  `phone` VARCHAR(20) DEFAULT NULL,
  `address` VARCHAR(255) DEFAULT NULL,
  `consignee` VARCHAR(64) DEFAULT NULL,
  `cancel_reason` VARCHAR(255) DEFAULT NULL,
  `rejection_reason` VARCHAR(255) DEFAULT NULL,
  `cancel_time` DATETIME DEFAULT NULL,
  `estimated_delivery_time` DATETIME DEFAULT NULL,
  `delivery_status` INT DEFAULT 1,
  `delivery_time` DATETIME DEFAULT NULL,
  `pack_amount` INT DEFAULT 0,
  `tableware_number` INT DEFAULT 0,
  `tableware_status` INT DEFAULT 1,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
