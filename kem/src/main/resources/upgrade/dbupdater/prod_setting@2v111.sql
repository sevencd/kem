CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "prod_setting";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
drop view if exists prod_setting;
 IF not EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN

 CREATE VIEW  `prod_setting` as 
SELECT DISTINCT
	0 AS `setting_id`,
	`a`.`manuscript_id` AS `manuscript_id`,
	`p23`.`parameter` AS `manuscript_name`,
	`p24`.`parameter` AS `manuscript_img`,
	`p25`.`parameter` AS `manuscript_maincolor`,
	`p26`.`parameter` AS `manuscript_summary`,
	`a`.`createtime` AS `createtime`,
	`a`.`enable` AS `enable`
FROM
	(
		(
			(
				(
					`prod_manuscript` `a`
					LEFT JOIN `prod_parameter` `p23` ON (
						(
							(
								`a`.`manuscript_id` = `p23`.`manuscript_id`
							)
							AND (`p23`.`parameter_type` = 23)
							AND (`p23`.`enable` = 0)
						)
					)
				)
				LEFT JOIN `prod_parameter` `p24` ON (
					(
						(
							`a`.`manuscript_id` = `p24`.`manuscript_id`
						)
						AND (`p24`.`parameter_type` = 24)
						AND (`p24`.`enable` = 0)
					)
				)
			)
			LEFT JOIN `prod_parameter` `p25` ON (
				(
					(
						`a`.`manuscript_id` = `p25`.`manuscript_id`
					)
					AND (`p25`.`parameter_type` = 25)
					AND (`p25`.`enable` = 0)
				)
			)
		)
		LEFT JOIN `prod_parameter` `p26` ON (
			(
				(
					`a`.`manuscript_id` = `p26`.`manuscript_id`
				)
				AND (`p26`.`parameter_type` = 26)
				AND (`p26`.`enable` = 0)
			)
		)
	);
END IF;
END;