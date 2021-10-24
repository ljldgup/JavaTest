drop table `user`;
CREATE TABLE `user` (
	`id` bigint(20) NOT NULL,
   `email` varchar(255) DEFAULT NULL,
   `name` varchar(255) NOT NULL,
   `amount` decimal(20) DEFAULT 0,
   PRIMARY KEY (`id`)
 ) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;