CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "contacts_main";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS contacts_main(
contacts_id varchar(255) NOT NULL PRIMARY KEY COMMENT 'key生成id',
is_delete int(11) NOT NULL COMMENT '是否删除，1删除，0没有',
user_id varchar(255) NOT NULL COMMENT '用户id',
createtime datetime NOT NULL COMMENT '开通时间',
updatetime datetime NOT NULL COMMENT '修改时间',
contacts_type int(11) NOT NULL COMMENT '联系人类型，1手机，2邮件'
) ENGINE=InnoDB comment='联系人主表' DEFAULT CHARSET=utf8;

	-- IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME='f4' AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
	-- ALTER TABLE db_ver ADD  f4 INT(1);
	-- END IF;
	-- 删除列
	-- IF EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME='f' AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		-- ALTER TABLE db_ver DROP COLUMN f;
	-- END IF;

DESC contacts_main;
END;