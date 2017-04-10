CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "prod_package_service_info";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
-- 产品服务套餐信息
CREATE TABLE IF NOT EXISTS  `prod_package_service_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `quantity` int(11) DEFAULT NULL COMMENT '数量',
  `package_service_id` int(11) DEFAULT NULL COMMENT '套餐服务编号',
  `service_id` int(11) DEFAULT NULL COMMENT '服务编号',
  `unit` int(11) DEFAULT NULL  COMMENT '单位  0 月数 1次数',
  `type` int(11) DEFAULT NULL COMMENT '类型  0 会员(域名与外连) 1 版权',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
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
	IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME="time_mode" AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
  			ALTER TABLE prod_package_service_info ADD time_mode INTEGER COMMENT "服务支付方式，会员的支付永远是0";
  			DELETE FROM prod_package_service_info;
  	END if;
DESC prod_package_service_info;
END;
