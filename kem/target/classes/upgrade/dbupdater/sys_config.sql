CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "sys_config";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
-- 产品服务
CREATE TABLE IF NOT EXISTS  `sys_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `sys_key` varchar(128) DEFAULT NULL COMMENT 'key',
  `sys_value` varchar(1024) DEFAULT NULL COMMENT 'value',
  `describe` varchar(1024) DEFAULT NULL COMMENT '描述',
  `type` int(11) DEFAULT NULL COMMENT '类型  0 支付宝 1 微信 2会员相关',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB comment='系统配置信息' AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


if not exists(select id from sys_config where sys_key="member_nearly_expired") then
	INSERT INTO `sys_config`
	(sys_key,sys_value,`describe`,`type`)
	VALUES ('member_nearly_expired', '3', '会员即将过期提醒时间', 2);
end if;

if not exists(select id from sys_config where sys_key="aliPublicKey") then
	INSERT INTO `sys_config`
	(sys_key,sys_value,`describe`,`type`)
	VALUES ('aliPublicKey', 'MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM+VOrCxwvHBAQyYnkl73qs7kFM5xCn0w6Uf2gQKqkIY7YU3HavDY7HNf9orKpTa+DRA3LNTWY0FWw2rz99AvNR3N6WA0WLOA+dHCUUd30QiimElAbg2yHTNoC6MJuWUh5eY3v0IOiwXSjEqXnOUXOT5aHcu0v1murSGxMMNeNPTAgMBAAECgYEAklJqkGp9M9QMXUinKr059BGEZmXttHVoA1k0YGz0Hqtboe975rtP5uEoIi0TUuIW2x5tc6ppEVKZIEXfvUrwzr02bKuaubU0T4i6yHURt23sYUbCHpn3zgWK6dWG23FYoXZknqFFUgteZuuq0RdSjvt3l0F24KOZQyRUxZtU3cECQQD6LFa8853vzHXmBGkL34gE4O2sxLcxIvzz/cbGo7p8wqI9dQSsCnL2YtHEj30PLLQXAhB60dDXSbDubplVvL/rAkEA1GryAnIIboJAHpLI4ABrUj3hwJ1xS2QIbyn0273QuB7wQ6PXTKuSHJ+KbvQcFJtf/7vtIvrjqdTFGV4ngCCpuQJAD69p/MzVcSyDk9lg8LKiJ5QmsrdeuQD1lSKrLNclIR9e5rWIhnTdQl9twYIxmBr4a5zghaLUEjt3kWtzx7Fe4wJARX6LmLM4APeKBLafE3HvqqNmNT8NLs4WWFAQtMd//ozYrDhxGrtS/RxRDQW+HhvLJ36TEYulag2bQjZkk+2buQJAP+yNfDkgXsdG/BzRiksodI8Ooa8Gj7AwwJLGnUn0aj4JLhCSvC3DSNQT1J5R3cDrNNx5Mdapsi+MEwJnFL2dzw==', '支付宝key', 0);
end if;

if not exists(select id from sys_config where sys_key="aliPartner") then
	INSERT INTO `sys_config`
	(sys_key,sys_value,`describe`,`type`)
	VALUES ('aliPartner', '2088311932748130', '支付宝号', 0);
end if;

if not exists(select id from sys_config where sys_key="aliSellerId") then
	INSERT INTO `sys_config`
	(sys_key,sys_value,`describe`,`type`)
	VALUES ('aliSellerId', '2088311932748130', '支付宝账号，必须和合作id一样', 0);
end if;

if not exists(select id from sys_config where sys_key="aliPrivateKey") then
	INSERT INTO `sys_config`
	(sys_key,sys_value,`describe`,`type`)
	VALUES ('aliPrivateKey', 'MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM+VOrCxwvHBAQyYnkl73qs7kFM5xCn0w6Uf2gQKqkIY7YU3HavDY7HNf9orKpTa+DRA3LNTWY0FWw2rz99AvNR3N6WA0WLOA+dHCUUd30QiimElAbg2yHTNoC6MJuWUh5eY3v0IOiwXSjEqXnOUXOT5aHcu0v1murSGxMMNeNPTAgMBAAECgYEAklJqkGp9M9QMXUinKr059BGEZmXttHVoA1k0YGz0Hqtboe975rtP5uEoIi0TUuIW2x5tc6ppEVKZIEXfvUrwzr02bKuaubU0T4i6yHURt23sYUbCHpn3zgWK6dWG23FYoXZknqFFUgteZuuq0RdSjvt3l0F24KOZQyRUxZtU3cECQQD6LFa8853vzHXmBGkL34gE4O2sxLcxIvzz/cbGo7p8wqI9dQSsCnL2YtHEj30PLLQXAhB60dDXSbDubplVvL/rAkEA1GryAnIIboJAHpLI4ABrUj3hwJ1xS2QIbyn0273QuB7wQ6PXTKuSHJ+KbvQcFJtf/7vtIvrjqdTFGV4ngCCpuQJAD69p/MzVcSyDk9lg8LKiJ5QmsrdeuQD1lSKrLNclIR9e5rWIhnTdQl9twYIxmBr4a5zghaLUEjt3kWtzx7Fe4wJARX6LmLM4APeKBLafE3HvqqNmNT8NLs4WWFAQtMd//ozYrDhxGrtS/RxRDQW+HhvLJ36TEYulag2bQjZkk+2buQJAP+yNfDkgXsdG/BzRiksodI8Ooa8Gj7AwwJLGnUn0aj4JLhCSvC3DSNQT1J5R3cDrNNx5Mdapsi+MEwJnFL2dzw==', '私钥', 0);
end if;

if not exists(select id from sys_config where sys_key="wxAppId") then
	INSERT INTO `sys_config`
	(sys_key,sys_value,`describe`,`type`)
	VALUES ('wxAppId', 'wx798c7df19560a294', '微信号', 1);
end if;

if not exists(select id from sys_config where sys_key="wxAppKey") then
	INSERT INTO `sys_config`
	(sys_key,sys_value,`describe`,`type`)
	VALUES ('wxAppKey', '92d27e2b0378ab772e152fca7f9ba38f', '微信key', 1);
end if;

if not exists(select id from sys_config where sys_key="wxMchId") then
	INSERT INTO `sys_config`
	(sys_key,sys_value,`describe`,`type`)
	VALUES ('wxMchId', '1262162601', '微信key', 1);
end if;


if not exists(select id from sys_config where sys_key="wxKey") then
	INSERT INTO `sys_config`
	(sys_key,sys_value,`describe`,`type`)
	VALUES ('wxKey', '8f8163ff65f750251b09bc757e13be6a', '微信key', 1);
end if;

if not exists(select id from sys_config where sys_key="hostConfig") then
	INSERT INTO `sys_config`
	(sys_key,sys_value,`describe`,`type`)
	VALUES ('hostConfig', '/_', '绑定域名所的配置', 3);
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
DESC sys_config;
END;
