-- takeout database initialization script for the current Spring Boot project

CREATE DATABASE IF NOT EXISTS `takeout`
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_general_ci;

USE `takeout`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `order_details`;
DROP TABLE IF EXISTS `orders`;
DROP TABLE IF EXISTS `user`;

-- user table: used by login/register
CREATE TABLE `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- orders table: used by checkout, merchant order list, queue/status updates
CREATE TABLE `orders` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(64) NOT NULL,
  `status` INT NOT NULL COMMENT '1=pending payment, 2=pending accept, 3=accepted, 4=delivering, 5=completed, 6=cancelled',
  `user_id` BIGINT NOT NULL,
  `address_book_id` BIGINT DEFAULT NULL,
  `order_time` DATETIME DEFAULT NULL,
  `checkout_time` DATETIME DEFAULT NULL,
  `pay_method` INT DEFAULT NULL COMMENT 'Current checkout uses 2=cash on delivery',
  `pay_status` INT NOT NULL DEFAULT 0 COMMENT '0=unpaid, 1=paid, 2=refunded',
  `amount` DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  `remark` VARCHAR(255) DEFAULT NULL,
  `user_name` VARCHAR(64) DEFAULT NULL,
  `phone` VARCHAR(50) DEFAULT NULL,
  `address` VARCHAR(255) DEFAULT NULL,
  `consignee` VARCHAR(64) DEFAULT NULL,
  `cancel_reason` VARCHAR(255) DEFAULT NULL,
  `rejection_reason` VARCHAR(255) DEFAULT NULL,
  `cancel_time` DATETIME DEFAULT NULL,
  `estimated_delivery_time` DATETIME DEFAULT NULL,
  `delivery_status` INT DEFAULT 1 COMMENT '1=deliver as soon as possible, 0=scheduled delivery',
  `delivery_time` DATETIME DEFAULT NULL,
  `pack_amount` INT NOT NULL DEFAULT 0,
  `tableware_number` INT NOT NULL DEFAULT 0,
  `tableware_status` INT DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_orders_number` (`number`),
  KEY `idx_orders_user_id` (`user_id`),
  KEY `idx_orders_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- order_details table: stores dish information for each order
CREATE TABLE `order_details` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `order_id` BIGINT NOT NULL,
  `dish_name` VARCHAR(255) NOT NULL,
  `quantity` INT NOT NULL DEFAULT 1,
  `price` DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  `create_time` DATETIME DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_order_details_order_id` (`order_id`),
  CONSTRAINT `fk_order_details_order_id`
    FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS = 1;

-- demo users
INSERT INTO `user` (`email`, `password`, `role`) VALUES
('merchant@example.com', '123456', 1),
('user@example.com', '123456', 0);
