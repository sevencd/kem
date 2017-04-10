CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "crawler_rule";
-- 表结构创建:
CREATE TABLE IF NOT EXISTS  `crawler_rule` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增长',
  `task_id` varchar(255) NOT NULL COMMENT '数据采集任务id',
  `type_id` int(20) DEFAULT  NULL COMMENT '行业id',
  `url` varchar(255) DEFAULT NULL COMMENT '网址',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
-- 表结构修改:
-- 修改type_id为type_id_one
IF EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME="type_id" AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		ALTER TABLE crawler_rule CHANGE COLUMN type_id type_id_one int(20);
END IF;
-- 增加type_id_two字段
IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME='type_id_two' AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		ALTER TABLE crawler_rule ADD  type_id_two INT(20) AFTER type_id_one;
END IF;
-- 修改createtime的默认值为数据库服务器的当前时间 
IF  EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME='createtime' AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
	ALTER TABLE `crawler_rule`
	MODIFY COLUMN `createtime`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' AFTER `url`;
END IF;
-- 修改url的长度为4096
IF EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME="url" AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		ALTER TABLE crawler_rule MODIFY COLUMN url varchar(4096);
END IF;


DESC crawler_rule;
END;
