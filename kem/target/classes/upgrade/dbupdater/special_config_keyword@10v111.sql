CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "special_config_keyword";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
drop view if exists special_config_keyword;
 IF not EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN

 CREATE VIEW  `special_config_keyword` as 
SELECT DISTINCT
	0 AS `keyword_id`,
	`p13`.`parameter` AS `model_config_id`,
	`p3`.`parameter` AS `keyword`
FROM
	(
		(
			`prod_manuscript` `a`
			LEFT JOIN `prod_parameter` `p13` ON (
				(
					(
						`a`.`manuscript_id` = `p13`.`manuscript_id`
					)
					AND (`p13`.`parameter_type` = 13)
					AND (`p13`.`enable` = 0)
				)
			)
		)
		LEFT JOIN `prod_parameter` `p3` ON (
			(
				(
					`a`.`manuscript_id` = `p3`.`manuscript_id`
				)
				AND (`p3`.`parameter_type` = 3)
				AND (`p3`.`enable` = 0)
			)
		)
	)
WHERE
	(
		`a`.`manuscript_type` IN (2, 3)
	);
END IF;
END;




