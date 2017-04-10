CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "sys_material_search_view";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
drop view if exists sys_material_search_view;
 IF not EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN

 CREATE VIEW  `sys_material_search_view` as 
(
	SELECT
		`uri`.`img_id` AS `materialId`,
		`uri`.`img_path` AS `thumbnail`,
		`um`.`terminal_type` AS `terminal_type`,
		`um`.`material_id` AS `material_category_id`,
		1 AS `material_type`,
		`uri`.`createtime` AS `createtime`,
		`um`.`material_name` AS `material_category`,
		`smi1`.`info_value` AS `keyword`,
		0 AS `material_status`,
		`smi2`.`info_value` AS `remark`,
		`uri`.`user_id` AS `user_id`,
		NULL AS `material_template`,
		NULL AS `client_type`,
		NULL AS `composite_category`,
		NULL AS `prepare_thumbnail`
	FROM
		(
			(
				(
					(
						(
							`user_resource_img` `uri`
							LEFT JOIN `user_material_img` `umi` ON (
								(
									`umi`.`img_id` = `uri`.`img_id`
								)
							)
						)
						LEFT JOIN `user_material` `um` ON (
							(
								`um`.`material_id` = `umi`.`material_id`
							)
						)
					)
					LEFT JOIN `sys_material_info` `smi` ON (
						(
							(
								`smi`.`material_id` = `umi`.`img_id`
							)
							AND (`smi`.`info_key` IN(8, 4, 10))
						)
					)
				)
				LEFT JOIN `sys_material_info` `smi1` ON (
					(
						(
							`uri`.`img_id` = `smi1`.`material_id`
						)
						AND (`smi1`.`info_key` = 4)
					)
				)
			)
			LEFT JOIN `sys_material_info` `smi2` ON (
				(
					(
						`uri`.`img_id` = `smi2`.`material_id`
					)
					AND (`smi2`.`info_key` = 10)
				)
			)
		)
	WHERE
		(
			`uri`.`img_type` <> 'thumbnail_group_controls'
		)
)
UNION
	(
		SELECT
			`cm`.`id` AS `materialId`,
			`cm`.`icon_url` AS `thumbnail`,
			`um`.`terminal_type` AS `terminal_type`,
			`um`.`material_id` AS `material_category_id`,
			3 AS `material_type`,
			`cm`.`add_time` AS `createtime`,
			`um`.`material_name` AS `material_category`,
			`smi1`.`info_value` AS `keyword`,
			`cm`.`state` AS `material_status`,
			`smi2`.`info_value` AS `remark`,
			'0' AS `user_id`,
			NULL AS `material_template`,
			`cm`.`client_type` AS `client_type`,
			`cm`.`type` AS `composite_category`,
			NULL AS `prepare_thumbnail`
		FROM
			(
				(
					(
						(
							(
								`composite_material` `cm`
								LEFT JOIN `sys_material_info` `smi` ON (
									(
										(
											`smi`.`material_id` = `cm`.`id`
										)
										AND (`smi`.`info_key` IN(8, 4, 10))
									)
								)
							)
							LEFT JOIN `user_material_img` `umi` ON ((`umi`.`img_id` = `cm`.`id`))
						)
						LEFT JOIN `user_material` `um` ON (
							(
								`um`.`material_id` = `umi`.`material_id`
							)
						)
					)
					LEFT JOIN `sys_material_info` `smi1` ON (
						(
							(
								`cm`.`id` = `smi1`.`material_id`
							)
							AND (`smi1`.`info_key` = 4)
						)
					)
				)
				LEFT JOIN `sys_material_info` `smi2` ON (
					(
						(
							`cm`.`id` = `smi2`.`material_id`
						)
						AND (`smi2`.`info_key` = 10)
					)
				)
			)
	)
UNION
	(
		SELECT
			`smm`.`material_id` AS `materialId`,
			`smi1`.`info_value` AS `thumbnail`,
			`um`.`terminal_type` AS `terminal_type`,
			`um`.`material_id` AS `material_category_id`,
			2 AS `material_type`,
			`smm`.`createtime` AS `createtime`,
			`um`.`material_name` AS `material_category`,
			`smi2`.`info_value` AS `keyword`,
			`smi3`.`info_value` AS `material_status`,
			`smi4`.`info_value` AS `remark`,
			`smm`.`user_id` AS `user_id`,
			`smi6`.`info_value` AS `material_template`,
			NULL AS `client_type`,
			NULL AS `composite_category`,
			`smi7`.`info_value` AS `prepare_thumbnail`
		FROM
			(
				(
					(
						(
							(
								(
									(
										(
											`sys_material_main` `smm`
											LEFT JOIN `user_material_img` `umi` ON (
												(
													`umi`.`img_id` = `smm`.`material_id`
												)
											)
										)
										LEFT JOIN `user_material` `um` ON (
											(
												`um`.`material_id` = `umi`.`material_id`
											)
										)
									)
									LEFT JOIN `sys_material_info` `smi1` ON (
										(
											(
												`smm`.`material_id` = `smi1`.`material_id`
											)
											AND (`smi1`.`info_key` = 3)
										)
									)
								)
								LEFT JOIN `sys_material_info` `smi2` ON (
									(
										(
											`smm`.`material_id` = `smi2`.`material_id`
										)
										AND (`smi2`.`info_key` = 4)
									)
								)
							)
							LEFT JOIN `sys_material_info` `smi3` ON (
								(
									(
										`smm`.`material_id` = `smi3`.`material_id`
									)
									AND (`smi3`.`info_key` = 5)
								)
							)
						)
						LEFT JOIN `sys_material_info` `smi4` ON (
							(
								(
									`smm`.`material_id` = `smi4`.`material_id`
								)
								AND (`smi4`.`info_key` = 10)
							)
						)
					)
					LEFT JOIN `sys_material_info` `smi6` ON (
						(
							(
								`smm`.`material_id` = `smi6`.`material_id`
							)
							AND (`smi6`.`info_key` = 9)
						)
					)
				)
				LEFT JOIN `sys_material_info` `smi7` ON (
					(
						(
							`smm`.`material_id` = `smi7`.`material_id`
						)
						AND (`smi7`.`info_key` = 0)
					)
				)
			)
		WHERE
			(`smm`.`enable` <> 1)
	);
END IF;
END;




