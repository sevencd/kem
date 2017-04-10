CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "user_plugin_activeplugin_drawprizedata";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS `user_plugin_activeplugin_drawprizedata` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `plugin_id` int(11) DEFAULT NULL COMMENT '插件ID',
  `phone_no` varchar(50) DEFAULT NULL COMMENT '电话号码',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `qq` varchar(50) DEFAULT NULL COMMENT 'qq号码',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `prize_name` varchar(50) DEFAULT NULL COMMENT '奖品名称',
  `prize_no` varchar(50) DEFAULT NULL COMMENT '中奖号码',
  `exchange_state` int(11) DEFAULT NULL COMMENT '兑奖状态',
  `exchangetime` datetime DEFAULT NULL COMMENT '兑奖时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=456 DEFAULT CHARSET=utf8;


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
DESC user_plugin_activeplugin_drawprizedata;
END;
