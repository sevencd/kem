CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "special_config_active";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
drop view if exists special_config_active;
 IF not EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN

 CREATE VIEW  `special_config_active` as 
SELECT DISTINCT
	0 AS `active_id`,
	`a`.`manuscript_id` AS `special_id`,
	str_to_date(
		`p16`.`parameter`,
		'%Y-%m-%d %H:%i:%s'
	) AS `start_time`,
	str_to_date(
		`p17`.`parameter`,
		'%Y-%m-%d %H:%i:%s'
	) AS `end_time`
FROM
	(
		(
			`prod_manuscript` `a`
			LEFT JOIN `prod_parameter` `p16` ON (
				(
					(
						`a`.`manuscript_id` = `p16`.`manuscript_id`
					)
					AND (`p16`.`parameter_type` = 16)
					AND (`p16`.`enable` = 0)
				)
			)
		)
		LEFT JOIN `prod_parameter` `p17` ON (
			(
				(
					`a`.`manuscript_id` = `p17`.`manuscript_id`
				)
				AND (`p17`.`parameter_type` = 17)
				AND (`p17`.`enable` = 0)
			)
		)
	)
WHERE
	(
		`a`.`manuscript_type` IN (2, 3)
	);
END IF;
END;




