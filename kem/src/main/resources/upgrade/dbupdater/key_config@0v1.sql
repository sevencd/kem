CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "key_config";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS  `key_config` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `prefix` longtext,
  `seed` bigint(20) DEFAULT NULL,
  `is_enable` tinyint(1) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `len` int(11) DEFAULT NULL,
  `step` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

if not exists(select id from key_config where prefix="1001") then
	INSERT INTO `key_config`
	(prefix,seed,is_enable,add_time,update_time,len,step)
	VALUES ('1001', 1, 1, now(), now(), 12, 50);
end if;

if not exists(select id from key_config where prefix="1002") then
	INSERT INTO `key_config` 
	(prefix,seed,is_enable,add_time,update_time,len,step)
	VALUES ('1002', 1, 1, now(), now(), 12, 50);
end if;

if not exists(select id from key_config where prefix="1003") then
	INSERT INTO `key_config` 
	(prefix,seed,is_enable,add_time,update_time,len,step)
	VALUES ('1003', 1, 1, now(), now(), 12, 50);
end if;

if not exists(select id from key_config where prefix="user") then
	INSERT INTO `key_config` 
	(prefix,seed,is_enable,add_time,update_time,len,step)
	VALUES ('user', 1, 1, now(), now(), 12, 50);
end if;

-- 表结构修改:
-- IF EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
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
-- END IF;
DESC key_config;
END;
