CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "special_config";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
drop view if exists special_config;
 IF not EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN

 CREATE VIEW  `special_config` as 
SELECT DISTINCT
	0 AS `config_id`,
	`p13`.`parameter` AS `model_config_id`,
	str_to_date(
		`p14`.`parameter`,
		'%Y-%m-%d %H:%i:%s'
	) AS `start_time`,
	str_to_date(
		`p15`.`parameter`,
		'%Y-%m-%d %H:%i:%s'
	) AS `end_time`,
	`b`.`manuscript_maincolor` AS `main_color`
FROM
	(
		(
			(
				(
					`prod_manuscript` `a`
					LEFT JOIN `prod_setting` `b` ON (
						(
							(
								`a`.`manuscript_id` = `b`.`manuscript_id`
							)
							AND (`b`.`enable` = 0)
						)
					)
				)
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
			LEFT JOIN `prod_parameter` `p14` ON (
				(
					(
						`a`.`manuscript_id` = `p14`.`manuscript_id`
					)
					AND (`p14`.`parameter_type` = 14)
					AND (`p14`.`enable` = 0)
				)
			)
		)
		LEFT JOIN `prod_parameter` `p15` ON (
			(
				(
					`a`.`manuscript_id` = `p15`.`manuscript_id`
				)
				AND (`p15`.`parameter_type` = 15)
				AND (`p15`.`enable` = 0)
			)
		)
	)
WHERE
	(
		`a`.`manuscript_type` IN (2, 3)
	);
END IF;
END;