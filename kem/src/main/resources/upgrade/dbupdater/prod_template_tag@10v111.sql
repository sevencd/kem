CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "prod_template_tag";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
drop view if exists prod_template_tag;
 IF not EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN

 CREATE VIEW  `prod_template_tag` as 
(
	SELECT DISTINCT
		1 AS `template_tag_id`,
		`a`.`manuscript_id` AS `template_id`,
		`b`.`parameter` AS `tag_name`,
		0 AS `tag_type`
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
			(`b`.`parameter_type` = 1)
			AND (`a`.`manuscript_type` = 1)
		)
)
UNION
	(
		SELECT
			1 AS `template_tag_id`,
			`c`.`manuscript_id` AS `template_id`,
			`c`.`parameter` AS `tag_name`,
			1 AS `tag_type`
		FROM
			(
				`prod_parameter` `c`
				LEFT JOIN `prod_manuscript` `d` ON (
					(
						`c`.`manuscript_id` = `d`.`manuscript_id`
					)
				)
			)
		WHERE
			(
				(`c`.`parameter_type` = 8)
				AND (`d`.`manuscript_type` = 1)
			)
	);
END IF;
END;