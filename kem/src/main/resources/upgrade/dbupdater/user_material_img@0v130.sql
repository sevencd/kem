CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "user_material_img";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS  `user_material_img` (
  `user_material_img_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `material_id` int(11) NOT NULL  COMMENT '类型id',
  `img_id` int(11) NOT NULL  COMMENT '图片id',
  PRIMARY KEY (`user_material_img_id`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8;
-- 表结构修改:

 IF EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
 
 	IF NOT EXISTS (select * from information_schema.statistics where table_schema=ts and TABLE_NAME=tn and COLUMN_NAME='material_id' and index_name !='primary') then
	 		ALTER TABLE `user_material_img` ADD INDEX user_material_img_material_id ( `material_id` );
	end if;
		
	IF NOT EXISTS (select * from information_schema.statistics where table_schema=ts and TABLE_NAME=tn and COLUMN_NAME='img_id' and index_name !='primary') then
	 		ALTER TABLE `user_material_img` ADD INDEX user_material_img_img_id ( `img_id` );
	end IF;
		 
END IF;
	alter table user_material_img modify column material_id varchar(255);	
	UPDATE user_material_img SET material_id = concat('mtr' , material_id) WHERE material_id NOT LIKE 'mtr%';	
	alter table user_material_img modify column img_id varchar(255);
	UPDATE user_material_img SET img_id = concat('img' , img_id) WHERE img_id NOT LIKE 'img%';
	
	UPDATE user_material_img SET img_id = (SELECT TRIM(LEADING 'img' FROM img_id)) WHERE img_id  LIKE 'img%' AND img_id  LIKE '%svg%';
	UPDATE user_material_img SET img_id = (SELECT TRIM(LEADING 'img' FROM img_id)) WHERE img_id  LIKE 'img%' AND img_id  LIKE '%cmtr%';
	
DESC user_material_img;
END;
