CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "sms_right_state";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS sms_right_state(
id int(4) NOT NULL AUTO_INCREMENT PRIMARY KEY,
createtime datetime NOT NULL COMMENT '开通时间',
updatetime datetime NOT NULL COMMENT '修改时间',
remaintimes int(11) NOT NULL COMMENT '剩余条数',
user_id varchar(255) NOT NULL COMMENT '用户id'
) ENGINE=InnoDB comment='短信状态表' AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

 IF EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
 	IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME="total" AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		 ALTER TABLE sms_right_state ADD  total int(11) DEFAULT 0 COMMENT '总的短信数量';
	END IF;
	-- IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME='f4' AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		-- ALTER TABLE db_ver ADD  f4 INT(1);
	-- END IF;
	-- 删除列
	-- IF EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME='f' AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		-- ALTER TABLE db_ver DROP COLUMN f;
	-- END IF;
 END IF;
DESC sms_right_state;
END;