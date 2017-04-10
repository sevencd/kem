CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "user_identify_user";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS  `user_identify_user` (
  `identify_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '身份标识id，自增长',
  `user_id` varchar(255) NOT NULL COMMENT '用户id',
  `identify` varchar(255) DEFAULT  NULL COMMENT '身份标识',
  `identify_type` int(11) DEFAULT  NULL COMMENT '身份标识类型：0:电话',
  `identify_state` int(11) DEFAULT NULL COMMENT '用户状态：0：禁用；1：启用',
  PRIMARY KEY (`identify_id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;

-- 表结构修改:
 IF EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
 
	IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME="identify_code" AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		 ALTER TABLE user_identify_user ADD  identify_code varchar(255) DEFAULT  NULL COMMENT '身份编码用于识别企业用户';
	END IF;
 END IF;
DESC user_identify_user;
END;
