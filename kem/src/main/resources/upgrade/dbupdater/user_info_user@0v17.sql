CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "user_info_user";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS  `user_info_user` (
  `info_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户信息id，自增长',
  `user_id` varchar(255) NOT NULL COMMENT '用户id',
  `info_type` int(11) DEFAULT  NULL COMMENT '信息类型：0:电话,1:姓名,2:邮件',
  `info` varchar(255) DEFAULT NULL COMMENT '信息',
  `info_state` int(11) DEFAULT NULL COMMENT '用户状态：0：禁用；1：启用',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;

-- 表结构修改:
 IF EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
 
  			alter table user_info_user modify  column user_id varchar(255);
  			
  			IF NOT EXISTS (select * from information_schema.statistics where table_schema=ts and TABLE_NAME=tn and COLUMN_NAME='user_id' and index_name !='primary') then
 				ALTER TABLE `user_info_user` ADD INDEX user_id ( `user_id` );
			end if;
			
			IF NOT EXISTS (select * from information_schema.statistics where table_schema=ts and TABLE_NAME=tn and COLUMN_NAME='info_type' and index_name !='primary') then
 				ALTER TABLE `user_info_user` ADD INDEX info_type ( `info_type` );
			end if;
			
			IF NOT EXISTS (select * from information_schema.statistics where table_schema=ts and TABLE_NAME=tn and COLUMN_NAME='info_state' and index_name !='primary') then
 				ALTER TABLE `user_info_user` ADD INDEX info_state ( `info_state` );
			end if;
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
DESC user_info_user;
END;
