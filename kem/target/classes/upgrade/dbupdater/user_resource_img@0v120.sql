CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "user_resource_img";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS `user_resource_img` (
  `img_id` int(11) NOT NULL AUTO_INCREMENT,
  `img_name` varchar(255) DEFAULT NULL,
  `img_path` varchar(255) DEFAULT NULL,
  `img_type` varchar(50) DEFAULT NULL,
  `createtime` datetime NOT NULL,
  `user_id` varchar(255) NOT NULL,
   `md5` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`img_id`)
) ENGINE=InnoDB AUTO_INCREMENT=339 DEFAULT CHARSET=utf8;

-- 表结构修改:
 IF EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
 
 	IF NOT EXISTS (select * from information_schema.statistics where table_schema=ts and TABLE_NAME=tn and COLUMN_NAME='img_id' and index_name !='primary') then
	 		ALTER TABLE `user_resource_img` ADD INDEX user_resource_img_img_id ( `img_id` );
	end if;
		
	IF NOT EXISTS (select * from information_schema.statistics where table_schema=ts and TABLE_NAME=tn and COLUMN_NAME='img_type' and index_name !='primary') then
	 		ALTER TABLE `user_resource_img` ADD INDEX user_resource_img_img_type ( `img_type` );
	end IF;
  	alter table user_resource_img modify  column user_id varchar(255);
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
	alter table user_resource_img modify column img_id varchar(255);	
	UPDATE user_resource_img SET img_id = concat('img' , img_id) WHERE img_id NOT LIKE 'img%';
	 
END IF;
DESC user_resource_img;
END;
