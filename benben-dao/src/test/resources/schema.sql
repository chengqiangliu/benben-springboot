CREATE TABLE `user_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(36) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(50),
  `group_no` int(5),
  `fail_login_count` int(1),
  `is_first_login` boolean,
  `last_logon_time` datetime,
  `logon_time` datetime,
  `is_locked` boolean,
  `insert_time` datetime,
  `update_time` datetime,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `group_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_no` int(5) NOT NULL,
  `group_name` varchar(20) NOT NULL,
  `secret_key` varchar(50),
  `is_admin_group` boolean,
  `api_list` varchar(255),
  `insert_time` datetime,
  `update_time` datetime,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `cash_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(36) NOT NULL,
  `currency` varchar(3) NOT NULL,
  `balance` decimal(10,2),
  `insert_time` datetime,
  `update_time` datetime,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `bankcard_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `card_no` varchar(19) NOT NULL,
  `bank_type` varchar(100) NOT NULL,
  `card_type` varchar(16) NOT NULL,
  `account_id` varchar(36) NOT NULL,
  `currency` varchar(3) NOT NULL,
  `credit_sum` decimal(10,2),
  `balance` decimal(10,2),
  `issue_date` datetime,
  `insert_time` datetime,
  `update_time` datetime,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;