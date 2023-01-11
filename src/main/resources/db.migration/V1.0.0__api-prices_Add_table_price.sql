SET MODE MYSQL;

CREATE TABLE IF NOT EXISTS `price` (
	`price_list_id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `brand_id` int NOT NULL,
    `product_id` int NOT NULL,
    `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `end_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `priority` int,
    `price` decimal(9,2) NOT NULL,
    `currency` VARCHAR(3)
    )ENGINE=InnoDB DEFAULT CHARSET=UTF8 AUTO_INCREMENT=1;

