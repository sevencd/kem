CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "prod_manuscript_total";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS  `prod_manuscript_total` (
  `total_id` bigint NOT NULL AUTO_INCREMENT COMMENT '统计id',
  `manuscript_id` varchar(255) DEFAULT NULL COMMENT '浏览稿件id',
   `ip` varchar(255) DEFAULT NULL COMMENT '请求的ip地址',
  `url` varchar(1024) DEFAULT NULL COMMENT '请求url',
  `request_time` datetime DEFAULT NULL COMMENT '请求时间',
  `stay_time` datetime DEFAULT NULL COMMENT '停留时间',
  `session_id` varchar(255) DEFAULT NULL COMMENT 'sessionid',
  PRIMARY KEY (`total_id`)
) ENGINE=InnoDB comment='统计数据收集表' AUTO_INCREMENT=95 DEFAULT CHARSET=utf8;
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
DESC prod_manuscript_total;
END;
