CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "user_plugin_info";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS `user_plugin_info` (
  `plugin_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '插件ID',
  `plugin_type` tinyint(4) DEFAULT NULL COMMENT '插件类型：1：表单插件；2：活动插件',
  `create_time` datetime DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL COMMENT '前台用户ID',
  `relation_id` varchar(50) DEFAULT NULL COMMENT '关联ID',
  `relation_type` tinyint(4) DEFAULT NULL COMMENT '关联ID类型： 1 模板 2 专题 3 推广',
  `is_used` tinyint(1) DEFAULT NULL COMMENT '是否在使用：1：在使用；0:未使用',
  PRIMARY KEY (`plugin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=564 DEFAULT CHARSET=utf8;

-- 表结构修改:
 IF EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
 		alter table user_plugin_info modify  column user_id varchar(255);
	-- 添加列
	-- IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME="file_name" AND TABLE_NAME=tn AND TABLE_SCHEMA=ts)) THEN
		-- ALTER TABLE db_ver ADD  file_name text;
	-- END IF;
	-- IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME="ver" AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		-- ALTER TABLE db_ver ADD  ver int(4);
	-- END IF;
	-- IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME='f4' AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		-- ALTER TABLE db_ver ADD  f4 INT(1);
	-- END IF;
	-- 删除列
	-- IF EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME='f' AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		-- ALTER TABLE db_ver DROP COLUMN f;
	-- END IF;
 END IF;
DESC user_plugin_info;
END;
