CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "auth_config";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS  `auth_config` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) DEFAULT NULL,
  `app_id` text,
  `app_secret` text,
  `is_enable` tinyint(1) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `redirect_uri` text,
  `auth_uri` text,
  `remark` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

if not exists(select id from auth_config where type='0') then 
	INSERT INTO `auth_config` 
	(type,app_id,app_secret,is_enable,add_time,update_time,redirect_uri,auth_uri,remark)
	VALUES (0, '3823533855', '75ba1eac140afcdda2ea176606eb784d', 1, now(), now(), 'http://www.qwt360.com/#/', 'https://api.weibo.com/oauth2/authorize?client_id=%s&redirect_uri=%s&scope=&state=%s&display=default&forcelogin=false&language=zh_CN', NULL);
end if;
if not exists(select id from auth_config where type='1') then 
	INSERT INTO `auth_config`
	(type,app_id,app_secret,is_enable,add_time,update_time,redirect_uri,auth_uri,remark) 
	VALUES 
	(1, '101348887', '99fcfb72673db064b0adae095bbf25b4', 1, now(), now(), 'http://www.qwt360.com/#/', 'https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=%s&redirect_uri=%s&state=%s', NULL);
end if;
if not exists(select id from auth_config where type='2') then 
	INSERT INTO `auth_config`
	(type,app_id,app_secret,is_enable,add_time,update_time,redirect_uri,auth_uri,remark) 
	VALUES 
	(2, 'wx4fd7cc4236957f9d', 'a11e64ef1803bcbe457f524d80d395d8', 1, now(), now(), 'http://www.qwt360.com/#/', 'https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect', NULL);
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
DESC auth_config;
END;
