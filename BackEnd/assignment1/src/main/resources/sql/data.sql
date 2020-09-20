-- Move this too the resource root folder when switching too MySQL database
INSERT IGNORE INTO `entity_user_type` (`id`, `created_at`, `name`, `updated_at`) VALUES
(1, '2020-09-16 01:25:32.959000', 'admin', NULL),
(2, '2020-09-16 01:25:33.058000', 'employee', NULL),
(3, '2020-09-16 01:25:33.127000', 'customer', NULL);

INSERT IGNORE INTO `entity_user` (`id`, `contact_number`, `created_at`, `name`, `password`, `updated_at`, `username`, `type_id`) VALUES
(1, '0000000000', '2020-09-16 01:25:35.407000', 'Jack', '$2a$10$ZhCVFVBQWOz5L2h4nZcTC.n0rCv2W7gc2cDTuKEJhvYBaRJ50vDua', NULL, 'Jacky', 3),
(2, '0000000000', '2020-09-16 01:25:35.545000', 'Chloe', '$2a$10$GlsqSBAIqfOezIADcmNWH.CGD3FJzSU31ZOPVjpl8/tvHBX4o2ViO', NULL, 'O\'Brian', 3),
(3, '0000000000', '2020-09-16 01:25:35.574000', 'Kim', '$2a$10$ljX.AAVJ650CFMXqpnt.5OAsya2c9sVlqdRhM745P6r7aJkBHQYm2', NULL, 'Bauer', 3),
(4, '0000000000', '2020-09-16 01:25:35.585000', 'David', '$2a$10$yWMdVm61HLpruV/xLS5MP.AXrNSGV3ndqVUSeUM/3rot8Ym4VKvve', NULL, 'Palmer', 1),
(5, '0000000000', '2020-09-16 01:25:35.631000', 'Michelle', '$2a$10$bRyNYEj4M20EnvWdUZCYH.vojQA6q1DRT4AALbfJvFnIJB07nfgXS', NULL, 'Dessler', 2),
(6, '0000000000', '2020-09-16 01:25:35.695000', 'Leslie', '$2a$10$4MZYqfUxm16BiQ0GTeav.ePdbYzRk2Lp/dh.PX4n3Dk2n8tF9yhxm', NULL, 'Messler', 2),
(7, '0000000000', '2020-09-16 01:25:35.710000', 'Joe', '$2a$10$SPedBo1HHAhcr6Rtu0MOU.TnNoU8pD9EznoSt2gdwOcMKWgHqfnsG', NULL, 'delete1', 3),
(8, '0000000000', '2020-09-16 01:25:35.727000', 'Joe', '$2a$10$DVTppcUvFWUocNpCv5nKreDU7UWNpiBPKKxP6Hmhit03xzYmY6mEO', NULL, 'delete2', 2),
(9, '0000000000', '2020-09-16 01:25:35.783000', 'Joe', '$2a$10$bIrf/K3NN.NMELpbHmD2Ou6WZq7UfSXFpvTF/yUZBj39rBjv7auI.', NULL, 'delete3', 1);

INSERT IGNORE INTO `entity_service` (`id`, `created_at`, `length`, `name`, `updated_at`) VALUES
(1, '2020-09-16 01:25:35.803000', 30, 'Freddie\'s Falafels', NULL),
(2, '2020-09-16 01:25:35.813000', 30, 'Joe\'s HotDogs', NULL),
(3, '2020-09-16 01:25:35.820000', 30, 'Service3', NULL),
(4, '2020-09-16 01:25:35.832000', 30, 'Service4', NULL),
(5, '2020-09-16 01:25:35.840000', 30, 'Service5', NULL);

INSERT IGNORE INTO `entity_schedule` (`id`, `created_at`, `end_date_time`, `start_date_time`, `updated_at`) VALUES
(1, '2020-09-16 01:25:35.859000', '2020-09-07 07:00:00.000000', '2020-09-06 23:00:00.000000', NULL),
(2, '2020-09-16 01:25:35.959000', '2020-09-07 14:00:00.000000', '2020-09-07 07:00:00.000000', NULL),
(3, '2020-09-16 01:25:35.974000', '2020-09-07 02:00:00.000000', '2020-09-06 19:00:00.000000', NULL);

INSERT IGNORE INTO `entity_booking` (`id`, `approved`, `created_at`, `end_date_time`, `start_date_time`, `updated_at`) VALUES
(1, b'0', '2020-09-16 01:25:35.997000', '2020-09-07 09:00:00.000000', '2020-09-07 07:00:00.000000', NULL),
(2, b'0', '2020-09-16 01:25:36.011000', '3019-08-03 10:50:00.000000', '3019-08-03 10:50:00.000000', '2020-09-16 01:25:37.797000');

INSERT IGNORE INTO `user_bookings` (`user_id`, `booking_id`) VALUES
(1, 1),
(2, 1),
(5, 1),
(1, 2),
(5, 2),
(7, 2),
(8, 2),
(9, 2);

INSERT IGNORE INTO `user_schedules` (`user_id`, `schedule_id`) VALUES
(4, 1),
(5, 2),
(6, 3),
(7, 3),
(8, 3),
(9, 3);

INSERT IGNORE INTO `user_services` (`user_id`, `service_id`) VALUES
(4, 1),
(5, 1),
(6, 1),
(4, 2),
(5, 2),
(6, 2),
(6, 3),
(6, 4),
(5, 5),
(6, 5),
(7, 5),
(8, 5),
(9, 5);