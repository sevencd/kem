CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "customer_info";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS  `customer_info` (
  `customer_info_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '客户信息主键',
  `customer_id` varchar(255) DEFAULT NULL COMMENT '客户编号',
  `customer_key` varchar(255) DEFAULT NULL COMMENT '信息key',
  `customer_value` varchar(255) DEFAULT NULL COMMENT '信息内容',
  PRIMARY KEY (`customer_info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;

-- 表结构修改:
 IF EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
 	IF NOT EXISTS (select * from information_schema.statistics where table_schema=ts and TABLE_NAME=tn and COLUMN_NAME='customer_id' and index_name !='customer_id') then
 		ALTER TABLE `customer_info` ADD INDEX customer_id ( `customer_id` );
	end if;
	IF NOT EXISTS (select * from information_schema.statistics where table_schema=ts and TABLE_NAME=tn and COLUMN_NAME='customer_key' and index_name !='customer_key') then
 		ALTER TABLE `customer_info` ADD INDEX customer_key ( `customer_key` );
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
DESC customer_info;
END;
