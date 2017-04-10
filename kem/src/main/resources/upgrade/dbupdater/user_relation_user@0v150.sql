CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "user_relation_user";
-- 琛ㄧ粨鏋勫垱寤�
-- 鍒犻櫎渚嬪瓙
-- DROP TABLE IF EXISTS db_ver;
-- 鍒涘缓渚嬪瓙
CREATE TABLE IF NOT EXISTS  `user_relation_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `user_id` varchar(255) NOT NULL  COMMENT '账号id',
  `father_user_id` varchar(255) NOT NULL  COMMENT '主账号id',
  `user_type` int(11) DEFAULT NULL COMMENT '用户类型 0主账户 1子账户',
  `user_sms` int(11) DEFAULT 0 COMMENT '短信消耗策略 0主账户 1子账户',
  `user_email` int(11) DEFAULT 0 COMMENT '邮件消耗策略 0主账户 1子账户',
  `user_b2b` int(11) DEFAULT 0 COMMENT 'b2b消耗策略 0主账户 1子账户',
  `user_customer` int(11) DEFAULT 0 COMMENT '客户消耗策略 0主账户 1子账户',
  `user_wechat` int(11) DEFAULT 0 COMMENT '微信消耗策略 0主账户 1子账户',
  `user_subuser` int(11) DEFAULT 0 COMMENT '子账户 0主账户 1子账户',
  `user_publictime` int(11) DEFAULT 0 COMMENT '发布次数0主账户 1子账户',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `state` int(11) DEFAULT NULL COMMENT '状态 0:启用1:停用'
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
DESC user_relation_user;
END;
