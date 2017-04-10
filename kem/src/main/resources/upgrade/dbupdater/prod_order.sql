CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "prod_order";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
-- 定单
CREATE TABLE IF NOT EXISTS  `prod_order` (
  `order_id` varchar(50) DEFAULT NULL COMMENT '订单号',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `user_id` varchar(128) DEFAULT NULL COMMENT '用户编号',
  `pay_status` int(11) DEFAULT NULL COMMENT '支付状态 0 等待支付 1 已支付',
  `pay_time`  datetime DEFAULT NULL COMMENT '支付时间',
  `pay_way` int(11) DEFAULT NULL COMMENT '支付方式  0 支付宝 1 微信 2 未知(如后台管理员代为下单)',
  `pay_amount` decimal(10,3) DEFAULT NULL COMMENT '实付金额',
  `amount_payable` decimal(10,3) DEFAULT NULL COMMENT '应付金额',
  `package_service_id` int(11) DEFAULT NULL COMMENT '套餐服务编号',
  `description` varchar(1024) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB comment='支付订单表' DEFAULT CHARSET=utf8;

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
DESC prod_order;
END;
