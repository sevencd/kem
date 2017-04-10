CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "contacts_info";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS contacts_info(
info_id int(4) NOT NULL AUTO_INCREMENT PRIMARY KEY,
contacts_id varchar(255) DEFAULT NULL COMMENT '关联contacts_main的主键',
info_key int(11) DEFAULT NULL COMMENT 'key',
info_value varchar(255) DEFAULT NULL COMMENT 'value',
enable int(11) DEFAULT NULL COMMENT '状态，'
) ENGINE=InnoDB comment='联系人info表' AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

	-- IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME='f4' AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		-- ALTER TABLE db_ver ADD  f4 INT(1);
	-- END IF;
	-- 删除列
	-- IF EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME='f' AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		-- ALTER TABLE db_ver DROP COLUMN f;
	-- END IF;

DESC contacts_info;
END;