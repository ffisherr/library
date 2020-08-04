CREATE TABLE IF NOT EXISTS `author` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(20)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;

ALTER TABLE `book` ADD `author_id` int AFTER `year`;
