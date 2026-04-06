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

-- Order details table (dishes in each order)
CREATE TABLE IF NOT EXISTS `order_details` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_id` BIGINT NOT NULL,
  `dish_name` VARCHAR(255) NOT NULL,
  `quantity` INT NOT NULL DEFAULT 1,
  `price` DECIMAL(10,2) DEFAULT 0.00,
  `create_time` DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_order_id` (`order_id`),
  CONSTRAINT `fk_order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
