CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "sys_material_main";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS  `sys_material_main` (
  `material_id` varchar(255) NOT NULL COMMENT '素材编号',
  `user_id` varchar(255) NOT NULL COMMENT '用户id',
  `createtime` datetime DEFAULT NULL COMMENT '素材上传时间',
  `updatetime` datetime DEFAULT NULL COMMENT '素材修改传时间',
  `enable` int(11) DEFAULT NULL COMMENT '状态：0：禁用；1：启用',
  PRIMARY KEY (`material_id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;

IF EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
 
 	IF NOT EXISTS (select * from information_schema.statistics where table_schema=ts and TABLE_NAME=tn and COLUMN_NAME='info_key' and index_name !='primary') then
	 		ALTER TABLE `sys_material_info` ADD INDEX sys_material_info_info_key ( `info_key` );
	end if;
		
	IF NOT EXISTS (select * from information_schema.statistics where table_schema=ts and TABLE_NAME=tn and COLUMN_NAME='material_id' and index_name !='primary') then
	 		ALTER TABLE `sys_material_info` ADD INDEX sys_material_info_material_id ( `material_id` );
	end IF;
	
	IF NOT EXISTS (select * from information_schema.statistics where table_schema=ts and TABLE_NAME=tn and COLUMN_NAME='enable' and index_name !='primary') then
	 		ALTER TABLE `sys_material_info` ADD INDEX sys_material_info_enable ( `enable` );
	end IF;
	 
END IF;
DESC sys_material_main;
END;
