CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "user_trial_user";
-- 表结构创建:
CREATE TABLE IF NOT EXISTS  `user_trial_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增长',
  `name` varchar(32)  COMMENT '姓名',
  `phoneNo` varchar(20) COMMENT '手机号',
  `company` varchar(64)	COMMENT '所在公司',
  `job` varchar(32)  COMMENT '职位',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='试用用户' DEFAULT CHARSET=utf8;
-- 表结构修改:
DESC user_trial_user;
END;
