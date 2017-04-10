CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "user_zdslog";
-- 琛ㄧ粨鏋勫垱寤�
-- 鍒犻櫎渚嬪瓙
-- DROP TABLE IF EXISTS db_ver;
-- 鍒涘缓渚嬪瓙
CREATE TABLE IF NOT EXISTS  `user_zdslog` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `user_id` varchar(255) NOT NULL  COMMENT '账号id',
  `param` longtext NOT NULL  COMMENT '参数',
  `result` longtext DEFAULT NULL  COMMENT '结果返回',
  `exception` longtext DEFAULT NULL  COMMENT '异常信息',
  `createtime` datetime DEFAULT NULL COMMENT '日志时间'
) ENGINE=InnoDB AUTO_INCREMENT=1 AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

-- 琛ㄧ粨鏋勪慨鏀�
 -- IF EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
  			-- alter table user_main_user modify  column user_id varchar(255);
	-- 娣诲姞鍒�
	-- IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME="file_name" AND TABLE_NAME=tn AND TABLE_SCHEMA=ts)) THEN
		-- ALTER TABLE db_ver ADD  file_name text;
	-- END IF;
	-- IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME="ver" AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		-- ALTER TABLE db_ver ADD  ver int(4);
	-- END IF;
	-- IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME='f4' AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		-- ALTER TABLE db_ver ADD  f4 INT(1);
	-- END IF;
	-- 鍒犻櫎鍒�
	-- IF EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME='f' AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		-- ALTER TABLE db_ver DROP COLUMN f;
	-- END IF;
 -- END IF;
DESC user_relation_resourcelog;
END;
