use app;

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



CREATE TABLE `app_config` (
    `id` INT AUTO_INCREMENT PRIMARY KEY comment '主键ID',
    `app_name` VARCHAR(50) NULL comment 'app名称',
    `version` VARCHAR(50) NULL comment 'app版本',
    `config_name` VARCHAR(255) NULL comment '配置具体名称',
    `key` CHAR(30) NULL comment '具体配置项的名称',
    `value` VARCHAR(512) NULL comment '具体配置项的值'
);

INSERT INTO `app_config` (`app_name`, `version`, `config_name`, `key`, `value`)
VALUES
    ('阶段项目', '1.0', 'app', 'app', '阶段项目'),
    ('阶段项目', '1.0', 'version', 'version', '1.0'),
    ('阶段项目', '1.0', 'downloader', 'MyIODownloader', 'day20240812.phase_project.downloader.MyIODownloader'), -- JsoupDownloader

    ('阶段项目', '1.0', 'storage', 'DbStorage', 'day20240812.phase_project.storage.DbStorage'),   -- ConsoleStorage、FileStorage
    ('阶段项目', '1.0', 'keywords', 'keywords', '小米,华为,闲置'),
    ('阶段项目', '1.0', 'notificator', 'ConsoleNotificator', 'day20240812.phase_project.notificator.ConsoleNotificator'),  -- EmailNotificator
    ('阶段项目', '1.0', 'from', 'from', 'from.xxx@qq.com'),
    ('阶段项目', '1.0', 'secret_token', 'secret_token', 'xxx'),
    ('阶段项目', '1.0', 'to', 'to', 'to.xxx@qq.com');


CREATE TABLE `url_parse` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `url` VARCHAR(255) NOT NULL,
    `parser` VARCHAR(255) NOT NULL
);

INSERT INTO `url_parse` (`name`, `url`, `parser`)
VALUES
    ('厦门小鱼', 'http://bbs.xmfish.com/thread-htm-fid-55-search-all-orderway-postdate-asc-DESC-page-1.html', 'day20240812.phase_project.parser.XmfishParser')


