CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "key_config";
CREATE TABLE IF NOT EXISTS  `key_config` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `prefix` longtext,
  `seed` bigint(20) DEFAULT NULL,
  `is_enable` tinyint(1) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `len` int(11) DEFAULT NULL,
  `step` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

if not exists(select id from key_config where prefix="1001") then
	INSERT INTO `key_config`
	(prefix,seed,is_enable,add_time,update_time,len,step)
	VALUES ('1001', 1, 1, now(), now(), 12, 50);
end if;

if not exists(select id from key_config where prefix="1002") then
	INSERT INTO `key_config` 
	(prefix,seed,is_enable,add_time,update_time,len,step)
	VALUES ('1002', 1, 1, now(), now(), 12, 50);
end if;

if not exists(select id from key_config where prefix="1003") then
	INSERT INTO `key_config` 
	(prefix,seed,is_enable,add_time,update_time,len,step)
	VALUES ('1003', 1, 1, now(), now(), 12, 50);
end if;

if not exists(select id from key_config where prefix="user") then
	INSERT INTO `key_config` 
	(prefix,seed,is_enable,add_time,update_time,len,step)
	VALUES ('user', 1, 1, now(), now(), 12, 50);
end if;

if not exists(select id from key_config where prefix="1004") then
	INSERT INTO `key_config` 
	(prefix,seed,is_enable,add_time,update_time,len,step)
	VALUES ('1004', 1, 1, now(), now(), 12, 50);
end if;

if not exists(select id from key_config where prefix="1005") then
	INSERT INTO `key_config` 
	(prefix,seed,is_enable,add_time,update_time,len,step)
	VALUES ('1005', 1, 1, now(), now(), 12, 50);
end if;

if exists(select id from key_config where prefix="manuscript") then
	delete FROM key_config where prefix="manuscript";
end if;

if not exists(select id from key_config where prefix="order") then
	INSERT INTO `key_config` 
	(prefix,seed,is_enable,add_time,update_time,len,step)
	VALUES ('order', 1, 1, now(), now(), 12, 50);
end if;
if not exists(select id from key_config where prefix="member") then
	INSERT INTO `key_config` 
	(prefix,seed,is_enable,add_time,update_time,len,step)
	VALUES ('member', 1, 1, now(), now(), 12, 50);
end if;

if not exists(select id from key_config where prefix="svg") then
	INSERT INTO `key_config` 
	(prefix,seed,is_enable,add_time,update_time,len,step)
	VALUES ('svg', 1, 1, now(), now(), 12, 50);
end if;

if not exists(select id from key_config where prefix="cmtr") then
	INSERT INTO `key_config` 
	(prefix,seed,is_enable,add_time,update_time,len,step)
	VALUES ('cmtr', 1, 1, now(), now(), 12, 50);
end if;

if not exists(select id from key_config where prefix="mtr") then
	INSERT INTO `key_config` 
	(prefix,seed,is_enable,add_time,update_time,len,step)
	VALUES ('mtr', 1, 1, now(), now(), 12, 50);
end if;

if not exists(select id from key_config where prefix="img") then
	INSERT INTO `key_config` 
	(prefix,seed,is_enable,add_time,update_time,len,step)
	VALUES ('img', 1, 1, now(), now(), 12, 50);
end if;
if not exists(select id from key_config where prefix="email") then
	INSERT INTO `key_config` 
	(prefix,seed,is_enable,add_time,update_time,len,step)
	VALUES ('email', 1, 1, now(), now(), 12, 50);
end if;

if not exists(select id from key_config where prefix="contact") then
	INSERT INTO `key_config` 
	(prefix,seed,is_enable,add_time,update_time,len,step)
	VALUES ('contact', 1, 1, now(), now(), 12, 50);
end if;
if not exists(select id from key_config where prefix="sms") then
	INSERT INTO `key_config` 
	(prefix,seed,is_enable,add_time,update_time,len,step)
	VALUES ('sms', 1, 1, now(), now(), 12, 50);
end if;
DESC key_config;
END;
