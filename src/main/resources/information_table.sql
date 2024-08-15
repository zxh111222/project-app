CREATE TABLE `information`
(
    `id`    int(11) NOT NULL AUTO_INCREMENT,
    `title` varchar(255) DEFAULT NULL,
    `url`   varchar(255) DEFAULT NULL UNIQUE,
    `createdAt` date DEFAULT NULL,
    `updatedAt` datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
);

ALTER TABLE `information` MODIFY createdAt TIMESTAMP NULL;
ALTER TABLE `information` MODIFY updatedAt TIMESTAMP NULL;
