CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "member_main";
-- 表结构创建:
CREATE TABLE IF NOT EXISTS  `member_main` (
  `member_id` varchar(64) NOT NULL COMMENT '会员服务ID',
  `user_id` varchar(64) NOT NULL COMMENT '用户ID',
  `starttime` datetime NOT NULL COMMENT '开始时间',
  `endtime` datetime NOT NULL COMMENT '结束时间',
  `status` int(11) NOT NULL COMMENT '会员服务状态：0：失效；1：有效',
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB comment='会员服务主表，记录了每个会员服务的状态及主要信息' DEFAULT CHARSET=utf8;

-- 表结构修改:
	
-- 添加列
IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME="package_service_id" AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
			
		 ALTER TABLE member_main ADD  package_service_id int(10) COMMENT '套餐id' AFTER user_id;
		 DELETE FROM member_main;
END IF;

IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME="package_service_price" AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		 ALTER TABLE member_main ADD  package_service_price double COMMENT '套餐价格' AFTER user_id;
END IF;

DESC member_main;
END;
