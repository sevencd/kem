CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "sys_identify_code";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS `sys_identify_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID，自增',
  `identity_code` varchar(50) DEFAULT NULL COMMENT '验证码信息，这里存储验证码是什么',
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户ID，验证码一定是某个用户的',
  `user_type` varchar(50) DEFAULT NULL COMMENT '用户类型，1：普通用户；2：设计师；3：匿名用户；4：流量用户；5：后台管理员',
  `module_code` varchar(50) DEFAULT NULL COMMENT '模块，具体模块编码需要的时候添加',
  `work_id` varchar(50) DEFAULT NULL COMMENT '具体流程的ID，根据模块编码及该id，可以定位到是哪个流程',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `deadline` datetime DEFAULT NULL COMMENT '截止时间',
  `status` varchar(10) DEFAULT NULL COMMENT '验证码状态：0：未验证；1：已验证',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3279 DEFAULT CHARSET=utf8;

-- 表结构修改:
 IF EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
 
  			alter table sys_identify_code modify  column user_id varchar(255);
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
DESC sys_identify_code;
END;
