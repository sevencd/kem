CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "sys_statemachine_work";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS `sys_statemachine_work` (
  `work_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '作业ID',
  `work_name` varchar(50) DEFAULT NULL COMMENT '作业名称，用以区分作业的类型',
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户ID',
  `user_type` varchar(50) DEFAULT NULL COMMENT '用户类型',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `work_finishstate` varchar(10) DEFAULT NULL COMMENT '作业是否已结束，0：未结束；1：已结束',
  PRIMARY KEY (`work_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 表结构修改:
 IF EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
 
  			alter table sys_statemachine_work modify  column user_id varchar(255);
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
DESC sys_statemachine_work;
END;
