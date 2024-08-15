CREATE TABLE `app_config` (
                              `id` INT AUTO_INCREMENT PRIMARY KEY,
                              `app_name` VARCHAR(50) NOT NULL,
                              `version` VARCHAR(50) NOT NULL,
                              `config_name` VARCHAR(255) NOT NULL,
                              `key` VARCHAR(255) NOT NULL,
                              `value` VARCHAR(255) NOT NULL
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
