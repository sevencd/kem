CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "user_traffic_user";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS  `user_traffic_user` (
  `trafficuserId` int(11) NOT NULL AUTO_INCREMENT COMMENT '流量用户主键',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `phoneNo` varchar(255) DEFAULT NULL COMMENT '电话',
  `qqNo` varchar(255) DEFAULT NULL COMMENT 'qq',
  `email` varchar(255) DEFAULT NULL COMMENT '邮件',
  `trafficuserType` int(11) DEFAULT NULL COMMENT '客户来源',
  `wechatNo` varchar(255) DEFAULT NULL COMMENT '微信',
  `vocation` varchar(255) DEFAULT NULL COMMENT '职业',
  `industry` varchar(255) DEFAULT NULL COMMENT '行业',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `extensionId` varchar(255) DEFAULT NULL COMMENT '推广id',
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`trafficuserId`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;

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
DESC user_traffic_user;

IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME="trafficfrom" AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
  			ALTER TABLE user_traffic_user ADD trafficfrom INTEGER COMMENT "用户来源";
  	END if;
  	
 IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME="address" AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
  			ALTER TABLE user_traffic_user ADD address varchar(255) COMMENT "地址";
  	END if;
  alter table user_traffic_user modify  column address varchar(255);

END;
