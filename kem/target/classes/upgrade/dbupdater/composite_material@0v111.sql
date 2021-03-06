CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "composite_material";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS  `composite_material` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `add_time` datetime DEFAULT  NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `icon_url` varchar(255) DEFAULT NULL COMMENT '图标',
  `type` varchar(255) DEFAULT NULL COMMENT '自定义类型',
  `state` int(11) DEFAULT NULL COMMENT '状态',
  `category` int(11) DEFAULT NULL COMMENT '类别',
  `data` longtext DEFAULT NULL COMMENT '数据',
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户编号',
  `client_type` int(11) DEFAULT NULL COMMENT '客户端类型 1 pc 2 mobile',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=275 DEFAULT CHARSET=utf8;
-- 表结构修改:
 IF EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
  			alter table composite_material modify  column user_id varchar(255);
	 IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME="client_type" AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		 ALTER TABLE composite_material ADD  client_type int(11) DEFAULT NULL COMMENT '客户端类型 1 pc 2 mobile';
	 END IF;	
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
	alter table composite_material modify  column data longtext;
	alter table composite_material modify  column id varchar(255);	
	UPDATE composite_material SET id = concat('cmtr' , id) WHERE id NOT LIKE 'cmtr%';
 END IF;
DESC composite_material;
END;
