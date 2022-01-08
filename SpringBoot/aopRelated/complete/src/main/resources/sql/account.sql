
CREATE TABLE `account` (
   `id` bigint(20) NOT NULL,
   `email` varchar(255) DEFAULT NULL,
   `name` varchar(255) NOT NULL,
   `amount` decimal(20,0) DEFAULT '0',
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

 insert into account values(1,'','first','1000');
 insert into account values(2,'','second','1000');

 use db_example2;
 select * from account;
