
-- Move this too the resource root folder when switching too MySQL database
  CREATE TABLE IF NOT EXISTS entity_booking (
  id bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  approved bit(1) DEFAULT NULL,
  created_at datetime(6) DEFAULT NULL,
  end_date_time datetime(6) DEFAULT NULL,
  start_date_time datetime(6) DEFAULT NULL,
  updated_at datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS entity_schedule (
  id bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  created_at datetime(6) DEFAULT NULL,
  end_date_time datetime(6) NOT NULL,
  start_date_time datetime(6) NOT NULL,
  updated_at datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS entity_service (
  id bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  created_at datetime(6) DEFAULT NULL,
  length int(11) DEFAULT NULL,
  name varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  updated_at datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS entity_user_type (
  id bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  created_at datetime(6) DEFAULT NULL,
  name varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  updated_at datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS entity_user (
  id bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  contact_number varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  created_at datetime(6) DEFAULT NULL,
  name varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  password varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  updated_at datetime(6) DEFAULT NULL,
  username varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  type_id bigint(20) DEFAULT NULL,
  UNIQUE KEY UK_70i2x6m79ud2wyhqvhh2mvb45 (username),
  KEY FKea07h6p90b80axxtxh2tj6my7 (type_id),
  CONSTRAINT FKea07h6p90b80axxtxh2tj6my7 FOREIGN KEY (type_id) REFERENCES entity_user_type (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS SPRING_SESSION (
  PRIMARY_ID char(36) CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  SESSION_ID char(36) CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  CREATION_TIME bigint(20),
  LAST_ACCESS_TIME bigint(20),
  MAX_INACTIVE_INTERVAL int(11),
  EXPIRY_TIME bigint(20),
  PRINCIPAL_NAME varchar(100) COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS SPRING_SESSION_ATTRIBUTES (
  SESSION_PRIMARY_ID char(36) COLLATE utf8_unicode_ci,
  ATTRIBUTE_NAME varchar(200) COLLATE utf8_unicode_ci,
  ATTRIBUTE_BYTES longblob
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS user_bookings (
  user_id bigint(20) NOT NULL,
  booking_id bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`booking_id`),
  KEY FKo0gg2gwl8qo1jexp8vldk6q6x (booking_id),
  CONSTRAINT FK1j23xoebo2q98q6x16qhdy71e FOREIGN KEY (user_id) REFERENCES entity_user (id),
  CONSTRAINT FKo0gg2gwl8qo1jexp8vldk6q6x FOREIGN KEY (booking_id) REFERENCES entity_booking (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS user_schedules (
  user_id bigint(20) NOT NULL,
  schedule_id bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`schedule_id`),
  KEY FK26kme1qewuibtpcstvgi4j14o (schedule_id),
  CONSTRAINT FK26kme1qewuibtpcstvgi4j14o FOREIGN KEY (schedule_id) REFERENCES entity_schedule (id),
  CONSTRAINT FK4di91b6id7nkkhskhh4w9rk31 FOREIGN KEY (user_id) REFERENCES entity_user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS user_services (
  user_id bigint(20) NOT NULL,
  service_id bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`service_id`),
  KEY FKaqvss8bi9gqw35o7lfm2rscky (service_id),
  CONSTRAINT FKaqvss8bi9gqw35o7lfm2rscky FOREIGN KEY (service_id) REFERENCES entity_service (id),
  CONSTRAINT FKbf8np2waf7p7tx28llab1p9r FOREIGN KEY (user_id) REFERENCES entity_user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

