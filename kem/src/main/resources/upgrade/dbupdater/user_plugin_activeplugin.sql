CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "user_plugin_activeplugin";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS  `user_plugin_activeplugin` (
  `recordId` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键记录',
  `plugin_id` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '1：九宫格，2：刮刮卡',
  `drawtime` int(11) DEFAULT NULL COMMENT '抽奖次数',
  `wintime` int(11) DEFAULT NULL COMMENT '得奖次数',
  `intervaltime` int(11) DEFAULT NULL COMMENT '间隔时间',
  `intervaltime_type` int(11) DEFAULT NULL COMMENT '间隔时间单位，1：分钟；2：小时；3：天',
  `prize_collect_info` varchar(200) DEFAULT NULL COMMENT 'json字符串',
  `prize_collect_required_info` varchar(200) DEFAULT NULL COMMENT 'json字符串',
  `outer_url` varchar(500) DEFAULT NULL COMMENT '网址链接',
  `merchant_phone` varchar(50) DEFAULT NULL COMMENT '商家电话',
  PRIMARY KEY (`recordId`)
) ENGINE=InnoDB AUTO_INCREMENT=361 DEFAULT CHARSET=utf8;
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
DESC user_plugin_activeplugin;
END;
