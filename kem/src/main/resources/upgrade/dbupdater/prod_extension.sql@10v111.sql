CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "prod_extension";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
drop view if exists prod_extension;
 IF not EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN

 CREATE VIEW  `prod_extension` as 
SELECT DISTINCT
	`a`.`manuscript_id` AS `extension_id`,
	`a`.`user_id` AS `user_id`,
	`b`.`manuscript_name` AS `extension_name`,
	`b`.`manuscript_img` AS `extension_img`,
	`p12`.`parameter` AS `special_id`,
	`p4`.`parameter` AS `special_name`,
	str_to_date(
		`p20`.`parameter`,
		'%Y-%m-%d %H:%i:%s'
	) AS `publish_time`,
	`p13`.`parameter` AS `model_config_id`,
	`p21`.`parameter` AS `extension_url`,
	`c`.`content` AS `context`,
	`a`.`terminal_type` AS `extension_type`,
	`a`.`state` AS `extension_state`,
	`a`.`createtime` AS `createtime`,
	`b`.`manuscript_summary` AS `summary`,
	`p19`.`parameter` AS `manuscript_id`
FROM
	(
		(
			(
				(
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
								LEFT JOIN `prod_content` `c` ON (
									(
										(
											`c`.`manuscript_id` = `a`.`manuscript_id`
										)
										AND (`c`.`enable` = 0)
									)
								)
							)
							LEFT JOIN `prod_parameter` `p12` ON (
								(
									(
										`p12`.`manuscript_id` = `a`.`manuscript_id`
									)
									AND (`p12`.`parameter_type` = 12)
									AND (`p12`.`enable` = 0)
								)
							)
						)
						LEFT JOIN `prod_parameter` `p20` ON (
							(
								(
									`p20`.`manuscript_id` = `a`.`manuscript_id`
								)
								AND (`p20`.`parameter_type` = 20)
								AND (`p20`.`enable` = 0)
							)
						)
					)
					LEFT JOIN `prod_parameter` `p13` ON (
						(
							(
								`p13`.`manuscript_id` = `a`.`manuscript_id`
							)
							AND (`p13`.`parameter_type` = 13)
							AND (`p13`.`enable` = 0)
						)
					)
				)
				LEFT JOIN `prod_parameter` `p21` ON (
					(
						(
							`p21`.`manuscript_id` = `a`.`manuscript_id`
						)
						AND (`p21`.`parameter_type` = 21)
						AND (`p21`.`enable` = 0)
					)
				)
			)
			LEFT JOIN `prod_parameter` `p19` ON (
				(
					(
						`p19`.`manuscript_id` = `a`.`manuscript_id`
					)
					AND (`p19`.`parameter_type` = 19)
					AND (`p19`.`enable` = 0)
				)
			)
		)
		LEFT JOIN `prod_parameter` `p4` ON (
			(
				(
					`p4`.`manuscript_id` = `a`.`manuscript_id`
				)
				AND (`p4`.`parameter_type` = 4)
				AND (`p4`.`enable` = 0)
			)
		)
	)
WHERE
	(`a`.`manuscript_type` = 3);
END IF;
END;