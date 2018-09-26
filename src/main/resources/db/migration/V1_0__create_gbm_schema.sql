CREATE DATABASE  IF NOT EXISTS `ui8y1kxcqib424pw`;
USE `ui8y1kxcqib424pw`;


CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ;


--
-- Table structure for table `privilege`
--

CREATE TABLE IF NOT EXISTS `privilege` (
  `privilege_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`privilege_id`)
);


--
-- Table structure for table `product`
--

CREATE TABLE IF NOT EXISTS `product` (
  `product_id` binary(16) NOT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`product_id`)
);


--
-- Table structure for table `product_quantity`
--

CREATE TABLE IF NOT EXISTS `product_quantity` (
  `product_quantity_id` binary(16) NOT NULL,
  `created_by` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `new_quantity` int(11) NOT NULL,
  `product_id` binary(16) NOT NULL,
  PRIMARY KEY (`product_quantity_id`),
  KEY `FKproduct_product_quantity` (`product_id`)
);



--
-- Table structure for table `role`
--

CREATE TABLE IF NOT EXISTS `role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `display_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `UKrole_name` (`name`)
);

--
-- Table structure for table `roles_privileges`
--

CREATE TABLE IF NOT EXISTS `roles_privileges` (
  `role_id` bigint(20) NOT NULL,
  `privilege_id` bigint(20) NOT NULL,
  KEY `FKprivilege_roles_privileges` (`privilege_id`),
  KEY `FKrole_proles_privileges` (`role_id`)
) ;


--
-- Table structure for table `login_attempt`
--
CREATE TABLE IF NOT EXISTS login_attempt (
  `attempt_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `attempts` int(11) NOT NULL DEFAULT 0,
  `last_modified` datetime NOT NULL,
  PRIMARY KEY (`attempt_id`)
);

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(100) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_by` varchar(100) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `attempt_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UKuser_email` (`email`),
  UNIQUE KEY `UKuser_login_attempt` (`attempt_id`),
  KEY `FKuser_login_attempt_id`(`attempt_id`)
);


--
-- Table structure for table `users_roles`
--

CREATE TABLE IF NOT EXISTS `users_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  KEY `FKrole_users_roles` (`role_id`),
  KEY `FKuser_users_roles` (`user_id`)
);
