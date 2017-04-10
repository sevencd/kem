CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "prod_template_keyword";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
drop view if exists prod_template_keyword;
 IF not EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN

 CREATE VIEW  `prod_template_keyword` as 
SELECT DISTINCT
	1 AS `template_keyword_id`,
	`b`.`manuscript_id` AS `template_id`,
	`b`.`parameter` AS `keyword`
FROM
	(
		`prod_parameter` `b`
		LEFT JOIN `prod_manuscript` `a` ON (
			(
				`a`.`manuscript_id` = `b`.`manuscript_id`
			)
		)
	)
WHERE
	(
		(`b`.`parameter_type` = 3)
		AND (`a`.`manuscript_type` = 1)
	);
END IF;
END;