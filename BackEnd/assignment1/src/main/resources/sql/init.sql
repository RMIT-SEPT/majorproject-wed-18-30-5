SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

CREATE DATABASE IF NOT EXISTS WED18305;
CREATE DATABASE IF NOT EXISTS WED18305Test;

USE WED18305;
-- Add tables to the database if they dont exist
CREATE TABLE IF NOT EXISTS `SPRING_SESSION` (
  `PRIMARY_ID` char(36) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `SESSION_ID` char(36) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `CREATION_TIME` bigint DEFAULT NULL,
  `LAST_ACCESS_TIME` bigint DEFAULT NULL,
  `MAX_INACTIVE_INTERVAL` int DEFAULT NULL,
  `EXPIRY_TIME` bigint DEFAULT NULL,
  `PRINCIPAL_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE IF NOT EXISTS `SPRING_SESSION_ATTRIBUTES` (
  `SESSION_PRIMARY_ID` char(36) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `ATTRIBUTE_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `ATTRIBUTE_BYTES` longblob
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


SET NAMES utf8;

CREATE TABLE IF NOT EXISTS `entity_booking` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `approval_status` int DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `end_date_time` datetime(6) DEFAULT NULL,
  `start_date_time` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE IF NOT EXISTS `entity_schedule` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `end_date_time` datetime(6) NOT NULL,
  `start_date_time` datetime(6) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE IF NOT EXISTS `entity_service` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `length` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE IF NOT EXISTS `entity_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contact_number` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `type_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_70i2x6m79ud2wyhqvhh2mvb45` (`username`),
  KEY `FKea07h6p90b80axxtxh2tj6my7` (`type_id`),
  CONSTRAINT `FKea07h6p90b80axxtxh2tj6my7` FOREIGN KEY (`type_id`) REFERENCES `entity_user_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE IF NOT EXISTS `entity_user_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE IF NOT EXISTS `user_bookings` (
  `user_id` bigint NOT NULL,
  `booking_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`booking_id`),
  KEY `FKo0gg2gwl8qo1jexp8vldk6q6x` (`booking_id`),
  CONSTRAINT `FK1j23xoebo2q98q6x16qhdy71e` FOREIGN KEY (`user_id`) REFERENCES `entity_user` (`id`),
  CONSTRAINT `FKo0gg2gwl8qo1jexp8vldk6q6x` FOREIGN KEY (`booking_id`) REFERENCES `entity_booking` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE IF NOT EXISTS `user_schedules` (
  `user_id` bigint NOT NULL,
  `schedule_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`schedule_id`),
  KEY `FK26kme1qewuibtpcstvgi4j14o` (`schedule_id`),
  CONSTRAINT `FK26kme1qewuibtpcstvgi4j14o` FOREIGN KEY (`schedule_id`) REFERENCES `entity_schedule` (`id`),
  CONSTRAINT `FK4di91b6id7nkkhskhh4w9rk31` FOREIGN KEY (`user_id`) REFERENCES `entity_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE IF NOT EXISTS `user_services` (
  `user_id` bigint NOT NULL,
  `service_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`service_id`),
  KEY `FKaqvss8bi9gqw35o7lfm2rscky` (`service_id`),
  CONSTRAINT `FKaqvss8bi9gqw35o7lfm2rscky` FOREIGN KEY (`service_id`) REFERENCES `entity_service` (`id`),
  CONSTRAINT `FKbf8np2waf7p7tx28llab1p9r` FOREIGN KEY (`user_id`) REFERENCES `entity_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- Test database
USE WED18305Test;
-- Drop everything and add tables and data
DROP TABLE IF EXISTS `SPRING_SESSION`;
CREATE TABLE `SPRING_SESSION` (
  `PRIMARY_ID` char(36) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `SESSION_ID` char(36) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `CREATION_TIME` bigint DEFAULT NULL,
  `LAST_ACCESS_TIME` bigint DEFAULT NULL,
  `MAX_INACTIVE_INTERVAL` int DEFAULT NULL,
  `EXPIRY_TIME` bigint DEFAULT NULL,
  `PRINCIPAL_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `SPRING_SESSION_ATTRIBUTES`;
CREATE TABLE `SPRING_SESSION_ATTRIBUTES` (
  `SESSION_PRIMARY_ID` char(36) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `ATTRIBUTE_NAME` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `ATTRIBUTE_BYTES` longblob
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


SET NAMES utf8;

DROP TABLE IF EXISTS `entity_booking`;
CREATE TABLE `entity_booking` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `approval_status` int DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `end_date_time` datetime(6) DEFAULT NULL,
  `start_date_time` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `entity_schedule`;
CREATE TABLE `entity_schedule` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `end_date_time` datetime(6) NOT NULL,
  `start_date_time` datetime(6) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `entity_service`;
CREATE TABLE `entity_service` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `length` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `entity_user`;
CREATE TABLE `entity_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `contact_number` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `type_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_70i2x6m79ud2wyhqvhh2mvb45` (`username`),
  KEY `FKea07h6p90b80axxtxh2tj6my7` (`type_id`),
  CONSTRAINT `FKea07h6p90b80axxtxh2tj6my7` FOREIGN KEY (`type_id`) REFERENCES `entity_user_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `entity_user_type`;
CREATE TABLE `entity_user_type` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `user_bookings`;
CREATE TABLE `user_bookings` (
  `user_id` bigint NOT NULL,
  `booking_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`booking_id`),
  KEY `FKo0gg2gwl8qo1jexp8vldk6q6x` (`booking_id`),
  CONSTRAINT `FK1j23xoebo2q98q6x16qhdy71e` FOREIGN KEY (`user_id`) REFERENCES `entity_user` (`id`),
  CONSTRAINT `FKo0gg2gwl8qo1jexp8vldk6q6x` FOREIGN KEY (`booking_id`) REFERENCES `entity_booking` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `user_schedules`;
CREATE TABLE `user_schedules` (
  `user_id` bigint NOT NULL,
  `schedule_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`schedule_id`),
  KEY `FK26kme1qewuibtpcstvgi4j14o` (`schedule_id`),
  CONSTRAINT `FK26kme1qewuibtpcstvgi4j14o` FOREIGN KEY (`schedule_id`) REFERENCES `entity_schedule` (`id`),
  CONSTRAINT `FK4di91b6id7nkkhskhh4w9rk31` FOREIGN KEY (`user_id`) REFERENCES `entity_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


DROP TABLE IF EXISTS `user_services`;
CREATE TABLE `user_services` (
  `user_id` bigint NOT NULL,
  `service_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`service_id`),
  KEY `FKaqvss8bi9gqw35o7lfm2rscky` (`service_id`),
  CONSTRAINT `FKaqvss8bi9gqw35o7lfm2rscky` FOREIGN KEY (`service_id`) REFERENCES `entity_service` (`id`),
  CONSTRAINT `FKbf8np2waf7p7tx28llab1p9r` FOREIGN KEY (`user_id`) REFERENCES `entity_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;