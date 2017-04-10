CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "sys_group_authorization";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS  `sys_group_authorization` (
  `id` int(11) NOT NULL  COMMENT '编号',
  `add_time` datetime  DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime  DEFAULT NULL COMMENT '修改时间',
  `group_id` int(11)  DEFAULT NULL COMMENT '组id 0后台用户  1普通用户  2设计师  3匿名用户 4流量用户 5后台管理员',
  `role_id` int(11)  DEFAULT NULL COMMENT '角色id',
  `enable` tinyint(1)  DEFAULT NULL COMMENT '是否可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
DELETE FROM `sys_group_authorization`;
INSERT INTO `sys_group_authorization`(
	`id`,
	`add_time`,
	`update_time`,
	`group_id`,
	`role_id`,
	`enable`)VALUES
	(1,now(),now(),0,1,1),
	(2,now(),now(),1,2,1),
	(3,now(),now(),2,3,1),
	(4,now(),now(),3,4,1),
	(5,now(),now(),4,5,1);
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
DESC sys_group_authorization;
END;
