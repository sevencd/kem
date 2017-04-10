CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "user_front_user";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
drop view if exists user_front_user;
 IF not EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN

 CREATE VIEW  `user_front_user` as 
SELECT
	`main`.`user_id` AS `user_id`,
	`info`.`info` AS `user_name`,
	`main`.`user_type` AS `user_type`,
	`identify`.`identify` AS `user_phone`,
	`info2`.`info` AS `user_email`,
	`main`.`createtime` AS `createtime`,
	`voucher`.`voucher` AS `login_pwd`,
	`main`.`state` AS `status`
FROM
	(
		(
			(
				(
					`user_main_user` `main`
					LEFT JOIN `user_identify_user` `identify` ON (
						(
							(
								`main`.`user_id` = `identify`.`user_id`
							)
							AND (
								`identify`.`identify_type` = 0
							)
							AND (
								`identify`.`identify_state` = 1
							)
						)
					)
				)
				LEFT JOIN `user_voucher_user` `voucher` ON (
					(
						(
							`voucher`.`user_id` = `main`.`user_id`
						)
						AND (`voucher`.`voucher_type` = 0)
						AND (`voucher`.`vouche_state` = 1)
					)
				)
			)
			LEFT JOIN `user_info_user` `info` ON (
				(
					(
						`info`.`user_id` = `main`.`user_id`
					)
					AND (`info`.`info_type` = 1)
					AND (`info`.`info_state` = 1)
				)
			)
		)
		LEFT JOIN `user_info_user` `info2` ON (
			(
				(
					`info2`.`user_id` = `main`.`user_id`
				)
				AND (`info2`.`info_type` = 2)
				AND (`info2`.`info_state` = 1)
			)
		)
	);
END IF;
END;