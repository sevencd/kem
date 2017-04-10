CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "prod_service";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
-- 产品服务
CREATE TABLE IF NOT EXISTS  `prod_service` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(128) DEFAULT NULL COMMENT '名称',
  `description` varchar(1024) DEFAULT NULL COMMENT '描述',
  `icon` varchar(256) DEFAULT NULL COMMENT '图标',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `type` int(11) DEFAULT NULL COMMENT '类型  0 会员(域名与外连) 1 版权',
  `unit` int(11) DEFAULT NULL COMMENT '单位 0 月 1 数量',
  `price` double DEFAULT NULL COMMENT '单价',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
if not exists(select id from prod_service where type=0) then 
	INSERT INTO `prod_service` 
	(name,description,icon,add_time,update_time,type,unit,price)
	VALUES ('会员','开通会员免费享受域名绑定和外链功能，免费获得去版权次数','',now(),now(),0,0,500);
end if;
if not exists(select id from prod_service where type=1) then 
	INSERT INTO `prod_service`
	(name,description,icon,add_time,update_time,type,unit,price) 
	VALUES 
	('去版权','去版权','',now(),now(),1,1,20);
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
DESC prod_service;
END;
