CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "email_contract";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS email_contract(
email_contract_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
email_id varchar(255) NOT NULL COMMENT '邮件ID',
to_name varchar(255) COMMENT '收件人名称',
email_addr varchar(255) NOT NULL COMMENT '收件人邮箱地址'
) ENGINE=InnoDB comment='邮件收件人表' AUTO_INCREMENT=1  DEFAULT CHARSET=utf8;

	-- IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME='f4' AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
	-- ALTER TABLE db_ver ADD  f4 INT(1);
	-- END IF;
	-- 删除列
	-- IF EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME='f' AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		-- ALTER TABLE db_ver DROP COLUMN f;
	-- END IF;

	IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME="id" AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		 ALTER TABLE email_contract ADD  id varchar(255);
	 END IF;	


DESC email_contract;
END;