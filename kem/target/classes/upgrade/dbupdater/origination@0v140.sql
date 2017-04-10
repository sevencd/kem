CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "origination";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS  `origination` (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `describe` varchar(255) DEFAULT NULL COMMENT '描述',
  `type` varchar(255) DEFAULT NULL COMMENT '来源类型',
  `is_delete` int(11) DEFAULT 0 COMMENT '是否删除，1删除，0没有',
  `createtime` datetime DEFAULT NULL,
  `updatetime` datetime DEFAULT NULL
) ENGINE=InnoDB comment='客户来源' AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

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

if not exists(select id from origination where type='0') then 
	INSERT INTO `origination`
	(`describe`,type,is_delete,createtime,updatetime) 
	VALUES 
	('快捷表单','0',0,now(),now());
end if;
if not exists(select id from origination where type='1') then 
	INSERT INTO `origination`
	(`describe`,type,is_delete,createtime,updatetime) 
	VALUES 
	('九宫格','1',0,now(),now());
end if;
if not exists(select id from origination where type='2') then 
	INSERT INTO `origination`
	(`describe`,type,is_delete,createtime,updatetime) 
	VALUES 
	('刮刮卡','2',0,now(),now());
end if;
if not exists(select id from origination where type='3') then 
	INSERT INTO `origination`
	(`describe`,type,is_delete,createtime,updatetime) 
	VALUES 
	('大转盘','3',0,now(),now());
end if;
if not exists(select id from origination where type='4') then 
	INSERT INTO `origination`
	(`describe`,type,is_delete,createtime,updatetime) 
	VALUES 
	('砸金蛋','4',0,now(),now());
end if;
if not exists(select id from origination where type='5') then 
	INSERT INTO `origination`
	(`describe`,type,is_delete,createtime,updatetime) 
	VALUES 
	('拆红包','5',0,now(),now());
end if;
if not exists(select id from origination where type='phoneNo') then 
	INSERT INTO `origination`
	(`describe`,type,is_delete,createtime,updatetime) 
	VALUES 
	('手机号','phoneNo',0,now(),now());
end if;
if not exists(select id from origination where type='email') then 
	INSERT INTO `origination`
	(`describe`,type,is_delete,createtime,updatetime) 
	VALUES 
	('邮箱','email',0,now(),now());
end if;
if not exists(select id from origination where type='qq') then 
	INSERT INTO `origination`
	(`describe`,type,is_delete,createtime,updatetime) 
	VALUES 
	('QQ号','qq',0,now(),now());
end if;
if not exists(select id from origination where type='wechat') then 
	INSERT INTO `origination`
	(`describe`,type,is_delete,createtime,updatetime) 
	VALUES 
	('微信','wechat',0,now(),now());
end if;

if not exists(select id from origination where type='manual') then 
	INSERT INTO `origination`
	(`describe`,type,is_delete,createtime,updatetime) 
	VALUES 
	('手动添加','manual',0,now(),now());
end if;

DESC origination;
END;