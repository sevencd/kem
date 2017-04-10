CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "template_collection";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS  `template_collection` (
  `collection_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '收藏id',
  `template_id` varchar(255) DEFAULT NULL COMMENT '模版id',
  `user_id` varchar(255) DEFAULT NULL COMMENT '用户',
  `collection_state` int(11) DEFAULT NULL COMMENT '收藏状态',
  `createtime` datetime DEFAULT NULL,
  `manuscript_type` int(11) DEFAULT 1 COMMENT '收藏种类 1 模版 2 专题 3推广 4优秀案例',
  PRIMARY KEY (`collection_id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;

-- 表结构修改:
 IF EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
  			alter table template_collection modify  column user_id varchar(255);

	 IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME="manuscript_type" AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		 ALTER TABLE template_collection ADD  manuscript_type int(11) DEFAULT 1 COMMENT '收藏种类 1 模版 2 专题 3推广 4优秀案例';
	 END IF;
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
 END IF;
DESC template_collection;
END;
