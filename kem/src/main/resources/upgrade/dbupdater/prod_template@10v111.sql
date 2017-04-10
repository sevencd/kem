CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "prod_template";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
drop view if exists prod_template;
 IF not EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN

 CREATE VIEW  `prod_template` as 
SELECT DISTINCT
	`a`.`manuscript_id` AS `template_id`,
	`a`.`user_id` AS `user_id`,
	`b`.`manuscript_img` AS `cover_img`,
	`b`.`manuscript_name` AS `template_name`,
	`b`.`manuscript_maincolor` AS `main_color`,
	`b`.`manuscript_summary` AS `summary`,
	`a`.`createtime` AS `createtime`,
	`a`.`state` AS `template_state`,
	`c`.`content` AS `template_content`,
	`p2`.`parameter` AS `publish_state`,
	`a`.`terminal_type` AS `template_type`,
	`p9`.`parameter` AS `verify_name`,
	str_to_date(
		`p5`.`parameter`,
		'%Y-%m-%d %H:%i:%s'
	) AS `verify_time`,
	str_to_date(
		`p6`.`parameter`,
		'%Y-%m-%d %H:%i:%s'
	) AS `shelf_time`,
	`p7`.`parameter` AS `bounced_reason`
FROM
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
					LEFT JOIN `prod_parameter` `p9` ON (
						(
							(
								`p9`.`manuscript_id` = `a`.`manuscript_id`
							)
							AND (`p9`.`parameter_type` = 9)
							AND (`p9`.`enable` = 0)
						)
					)
				)
				LEFT JOIN `prod_parameter` `p5` ON (
					(
						(
							`p5`.`manuscript_id` = `a`.`manuscript_id`
						)
						AND (`p5`.`parameter_type` = 5)
						AND (`p5`.`enable` = 0)
					)
				)
			)
			LEFT JOIN `prod_parameter` `p6` ON (
				(
					(
						`p6`.`manuscript_id` = `a`.`manuscript_id`
					)
					AND (`p6`.`parameter_type` = 6)
					AND (`p6`.`enable` = 0)
				)
			)
		)
		LEFT JOIN `prod_parameter` `p7` ON (
			(
				(
					`p7`.`manuscript_id` = `a`.`manuscript_id`
				)
				AND (`p7`.`parameter_type` = 7)
				AND (`p7`.`enable` = 0)
			)
		)
	)
WHERE
	(`a`.`manuscript_type` = 1);
END IF;
END;