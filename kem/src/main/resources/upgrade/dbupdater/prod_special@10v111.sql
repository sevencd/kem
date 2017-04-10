CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "prod_special";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
drop view if exists prod_special;
 IF not EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN

 CREATE VIEW  `prod_special` as 
SELECT DISTINCT
	`a`.`manuscript_id` AS `special_id`,
	`a`.`user_id` AS `user_id`,
	`b`.`manuscript_name` AS `special_name`,
	`p10`.`parameter` AS `template_id`,
	`a`.`createtime` AS `createtime`,
	`b`.`manuscript_img` AS `cover_img`,
	`c`.`content` AS `context`,
	`b`.`manuscript_summary` AS `summary`,
	str_to_date(
		`p20`.`parameter`,
		'%Y-%m-%d %H:%i:%s'
	) AS `publish_time`,
	`p4`.`parameter` AS `publish_name`,
	`p2`.`parameter` AS `publish_state`,
	`p13`.`parameter` AS `model_config_id`,
	`p22`.`parameter` AS `active_type`,
	0 AS `active_config`,
	`a`.`state` AS `special_state`,
	`a`.`terminal_type` AS `special_type`,
	`p18`.`parameter` AS `extension_id`,
	`p18`.`parameter` AS `manuscript_id`
FROM
	(
		(
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
									LEFT JOIN `prod_parameter` `p10` ON (
										(
											(
												`p10`.`manuscript_id` = `a`.`manuscript_id`
											)
											AND (`p10`.`parameter_type` = 10)
											AND (`p10`.`enable` = 0)
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
						LEFT JOIN `prod_parameter` `p2` ON (
							(
								(
									`p2`.`manuscript_id` = `a`.`manuscript_id`
								)
								AND (`p2`.`parameter_type` = 2)
								AND (`p2`.`enable` = 0)
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
				LEFT JOIN `prod_parameter` `p22` ON (
					(
						(
							`p22`.`manuscript_id` = `a`.`manuscript_id`
						)
						AND (`p22`.`parameter_type` = 22)
						AND (`p22`.`enable` = 0)
					)
				)
			)
			LEFT JOIN `prod_parameter` `p18` ON (
				(
					(
						`p18`.`manuscript_id` = `a`.`manuscript_id`
					)
					AND (`p18`.`parameter_type` = 18)
					AND (`p18`.`enable` = 0)
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
WHERE
	(`a`.`manuscript_type` = 2);
END IF;
END;