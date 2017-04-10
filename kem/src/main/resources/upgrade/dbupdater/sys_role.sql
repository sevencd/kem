CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "sys_role";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS  `sys_role` (
  `id` int(11) NOT NULL  COMMENT '编号',
  `name` varchar(1024) DEFAULT NULL COMMENT '名称',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(2048) DEFAULT NULL COMMENT '备注',
  `enable` tinyint(1) DEFAULT NULL COMMENT '是否可用',
  `is_delete` tinyint(1) DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
-- 1后台用户  2普通用户  3设计师  4匿名用户 5流量用户 6后台管理员
DELETE FROM `sys_role`;
INSERT INTO `sys_role`(
	`id`,
	`name`,
	`add_time`,
	`update_time`,
	`remark`,
	`enable`,
	`is_delete`)VALUES
	(1,'后台用户',now(),now(),'后台用户',1,0),
	(2,'普通用户',now(),now(),'普通用户',1,0),
	(3,'设计师',now(),now(),'设计师',1,0),
	(4,'匿名用户',now(),now(),'匿名用户',1,0),
	(5,'流量用户',now(),now(),'流量用户',1,0);
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
DESC sys_role;
END;
