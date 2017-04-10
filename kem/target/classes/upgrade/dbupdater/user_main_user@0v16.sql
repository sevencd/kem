CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "user_main_user";
-- 琛ㄧ粨鏋勫垱寤�
-- 鍒犻櫎渚嬪瓙
-- DROP TABLE IF EXISTS db_ver;
-- 鍒涘缓渚嬪瓙
CREATE TABLE IF NOT EXISTS  `user_main_user` (
  `user_id` varchar(255) NOT NULL  COMMENT '用户主表编号',
  `user_type` int(11) DEFAULT NULL COMMENT '用户类型0:匿名用户1前台用户2:后台用户3:匿名用户4:流量用户,-1:非法用户:',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `state` int(11) DEFAULT NULL COMMENT '状态0:启用1',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

-- 琛ㄧ粨鏋勪慨鏀�
 IF EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
  			alter table user_main_user modify  column user_id varchar(255);
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
 END IF;
DESC user_main_user;
END;
