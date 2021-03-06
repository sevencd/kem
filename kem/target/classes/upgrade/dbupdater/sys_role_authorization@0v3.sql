CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "sys_role_authorization";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS  `sys_role_authorization` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `resource_id` int(11) DEFAULT NULL COMMENT '资源id',
  `authority_code` int(11) DEFAULT NULL COMMENT '权限码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
DELETE FROM `sys_role_authorization`;
INSERT INTO `sys_role_authorization`(
	`id`,
  	`add_time`,
  	`update_time`,
  	`role_id`,
  	`resource_id`,
  	`authority_code`)VALUES
	(1,now(),now(),4,1,1),
	(2,now(),now(),1,2,1),
	(3,now(),now(),2,2,1),
	(4,now(),now(),3,2,1),
	(5,now(),now(),1,3,1),
	(6,now(),now(),2,3,1),
	(7,now(),now(),3,3,1),
	(8,now(),now(),4,3,1),
	(9,now(),now(),1,4,1),
	(10,now(),now(),2,4,1),
	(11,now(),now(),3,4,1),
	(12,now(),now(),4,4,1),
	(13,now(),now(),4,5,1),
	(14,now(),now(),1,6,1),
	(15,now(),now(),4,7,1),
	(172,now(),now(),0,7,1),
	(173,now(),now(),1,7,1),
	(174,now(),now(),2,7,1),
	(175,now(),now(),3,7,1),
	(176,now(),now(),5,7,1),
	(16,now(),now(),2,8,1),
	(17,now(),now(),3,8,1),
	(18,now(),now(),2,9,1),
	(19,now(),now(),3,9,1),
	(20,now(),now(),2,10,1),
	(21,now(),now(),3,10,1),
	(22,now(),now(),1,11,1),
	(23,now(),now(),2,11,1),
	(24,now(),now(),3,11,1),
	(25,now(),now(),4,11,1),
	(26,now(),now(),4,12,1),
	(27,now(),now(),4,13,1),
	(28,now(),now(),4,14,1),
	(29,now(),now(),1,15,1),
	(30,now(),now(),2,15,1),
	(31,now(),now(),3,15,1),
	(32,now(),now(),4,15,1),
	(33,now(),now(),3,16,1),
	(34,now(),now(),3,17,1),
	(35,now(),now(),3,18,1),
	(36,now(),now(),3,19,1),
	(37,now(),now(),3,20,1),
	(38,now(),now(),3,21,1),
	(39,now(),now(),3,22,1),
	(40,now(),now(),5,23,1),
	(41,now(),now(),1,24,1),
	(42,now(),now(),3,25,1),
	(43,now(),now(),3,26,1),
	(44,now(),now(),3,27,1),
	(45,now(),now(),1,28,1),
	(46,now(),now(),2,28,1),
	(47,now(),now(),3,28,1),
	(48,now(),now(),2,29,1),
	(49,now(),now(),3,29,1),
	(50,now(),now(),2,30,1),
	(51,now(),now(),3,30,1),
	(52,now(),now(),1,31,1),
	(53,now(),now(),2,31,1),
	(54,now(),now(),3,31,1),
	(55,now(),now(),1,32,1),
	(56,now(),now(),2,32,1),
	(57,now(),now(),3,32,1),
	(58,now(),now(),1,33,1),
	(59,now(),now(),2,33,1),
	(60,now(),now(),3,33,1),
	(61,now(),now(),1,34,1),
	(62,now(),now(),2,34,1),
	(63,now(),now(),3,34,1),
	(64,now(),now(),1,35,1),
	(65,now(),now(),2,35,1),
	(66,now(),now(),3,35,1),
	(67,now(),now(),1,36,1),
	(68,now(),now(),2,36,1),
	(69,now(),now(),3,36,1),
	(70,now(),now(),1,37,1),
	(71,now(),now(),2,37,1),
	(72,now(),now(),3,37,1),
	(73,now(),now(),1,38,1),
	(74,now(),now(),1,39,1),
	(75,now(),now(),2,39,1),
	(76,now(),now(),3,39,1),
	(77,now(),now(),4,39,1),
	(78,now(),now(),1,40,1),
	(79,now(),now(),2,40,1),
	(80,now(),now(),3,40,1),
	(81,now(),now(),4,40,1),
	(82,now(),now(),1,41,1),
	(83,now(),now(),2,41,1),
	(84,now(),now(),3,41,1),
	(85,now(),now(),4,41,1),
	(86,now(),now(),1,42,1),
	(87,now(),now(),2,42,1),
	(88,now(),now(),3,42,1),
	(89,now(),now(),4,42,1),
	(90,now(),now(),4,43,1),
	(91,now(),now(),4,44,1),
	(92,now(),now(),4,45,1),
	(93,now(),now(),2,46,1),
	(94,now(),now(),1,47,1),
	(95,now(),now(),2,47,1),
	(96,now(),now(),3,47,1),
	(97,now(),now(),4,47,1),
	(98,now(),now(),2,48,1),
	(99,now(),now(),2,49,1),
	(100,now(),now(),2,50,1),
	(101,now(),now(),2,51,1),
	(102,now(),now(),2,52,1),
	(103,now(),now(),2,53,1),
	(104,now(),now(),2,54,1),
	(105,now(),now(),2,55,1),
	(106,now(),now(),2,56,1),
	(107,now(),now(),1,57,1),
	(108,now(),now(),2,57,1),
	(109,now(),now(),1,58,1),
	(110,now(),now(),2,58,1),
	(111,now(),now(),3,58,1),
	(112,now(),now(),4,58,1),
	(113,now(),now(),2,59,1),
	(114,now(),now(),3,59,1),
	(115,now(),now(),1,60,1),
	(116,now(),now(),2,60,1),
	(117,now(),now(),3,60,1),
	(118,now(),now(),4,60,1),
	(119,now(),now(),1,61,1),
	(120,now(),now(),2,61,1),
	(121,now(),now(),3,61,1),
	(122,now(),now(),4,61,1),
	(123,now(),now(),1,62,1),
	(124,now(),now(),2,62,1),
	(125,now(),now(),3,62,1),
	(126,now(),now(),4,62,1),
	(127,now(),now(),2,63,1),
	(128,now(),now(),2,64,1),
	(129,now(),now(),2,65,1),
	(130,now(),now(),1,66,1),
	(131,now(),now(),2,66,1),
	(132,now(),now(),2,67,1),
	(133,now(),now(),1,68,1),
	(134,now(),now(),1,69,1),
	(135,now(),now(),2,70,1),
	(136,now(),now(),2,71,1),
	(137,now(),now(),2,72,1),
	(138,now(),now(),4,72,1),
	(139,now(),now(),2,73,1),
	(140,now(),now(),4,73,1),
	(141,now(),now(),2,74,1),
	(142,now(),now(),2,75,1),
	(143,now(),now(),1,76,1),
	(144,now(),now(),1,77,1),
	(145,now(),now(),2,77,1),
	(146,now(),now(),3,77,1),
	(147,now(),now(),1,78,1),
	(148,now(),now(),2,79,1),
	(149,now(),now(),3,79,1),
	(150,now(),now(),2,80,1),
	(151,now(),now(),3,80,1),
	(152,now(),now(),2,81,1),
	(153,now(),now(),3,81,1),
	(154,now(),now(),3,82,1),
	(155,now(),now(),2,83,1),
	(156,now(),now(),3,83,1),
	(157,now(),now(),2,84,1),
	(158,now(),now(),3,84,1),
	(159,now(),now(),2,85,1),
	(160,now(),now(),3,85,1),
	(161,now(),now(),1,86,1),
	(162,now(),now(),2,86,1),
	(163,now(),now(),3,86,1),
	(164,now(),now(),4,86,1),
	(165,now(),now(),1,86,1),
	(166,now(),now(),2,87,1),
	(167,now(),now(),3,87,1),
	(168,now(),now(),4,87,1),
	(169,now(),now(),1,88,1),
	(170,now(),now(),2,88,1),
	(171,now(),now(),3,88,1),
-- huangyi
	(2000,now(),now(),2,200,1),
	(2001,now(),now(),3,200,1),
	
	(2002,now(),now(),2,201,1),
	(2003,now(),now(),2,202,1),
	(2004,now(),now(),3,202,1),
	(2005,now(),now(),2,203,1),
	(2006,now(),now(),3,203,1),
	
	(2007,now(),now(),2,204,1),
	(2008,now(),now(),3,204,1),
	
	(2009,now(),now(),2,205,1),
	(2010,now(),now(),3,205,1),
	(2011,now(),now(),4,205,1),
	
	(2012,now(),now(),2,206,1),
	
	(2013,now(),now(),1,207,1),
	(2014,now(),now(),2,207,1),
	(2015,now(),now(),3,207,1),
	(2016,now(),now(),4,207,1),
	(2017,now(),now(),5,207,1),
	
	(2018,now(),now(),2,208,1),
	(2019,now(),now(),3,208,1),
	
-- huangyi

-- xuzhe
	(4000,now(),now(),1,401,1),
	(4001,now(),now(),2,401,1),
	(4002,now(),now(),3,401,1),
	(4003,now(),now(),1,402,1),
	(4004,now(),now(),2,402,1),
	(4005,now(),now(),1,403,1),
	(4006,now(),now(),1,404,1),
	(4007,now(),now(),1,405,1),
-- xuzhe

	(3001,now(),now(),1,301,1),
	(3002,now(),now(),1,302,1),
	(3003,now(),now(),1,303,1),
	(3004,now(),now(),1,304,1),
	(3005,now(),now(),2,305,1),
	(3006,now(),now(),2,306,1);

-- 表结构修改:
-- IF EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
	-- 添加列
	-- IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME="file_name" AND TABLE_NAME=tn AND TABLE_SCHEMA=ts)) THEN
		-- ALTER TABLE db_ver ADD  file_name text;
	-- END IF;
	-- IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME="ver" AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		-- ALTER TABLE db_ver ADD  ver int(4);
	-- END IF;
	-- IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME='f4' AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		-- ALTER TABLE db_ver ADD  f4 INT(1);
	-- END IF;
	-- 删除列
	-- IF EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME='f' AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		-- ALTER TABLE db_ver DROP COLUMN f;
	-- END IF;
-- END IF;
DESC audit_log;
END;
