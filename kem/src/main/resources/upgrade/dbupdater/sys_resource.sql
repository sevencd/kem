CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "sys_resource";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS  `sys_resource` (
  `id` int(11) NOT NULL  COMMENT '编号',
  `uri` varchar(2048) DEFAULT NULL COMMENT '统一标识符',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `enable` tinyint(1) DEFAULT NULL COMMENT '是否可用',
  `remark` varchar(2048) DEFAULT NULL COMMENT '备注',
  `name` varchar(2048) DEFAULT NULL COMMENT '名称',
  `is_delete` tinyint(1) DEFAULT NULL COMMENT '逻辑删除',
  `type` int(11) DEFAULT NULL COMMENT '类型',
  `app_id`  varchar(1024) DEFAULT NULL COMMENT '应用id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
DELETE FROM sys_resource;
INSERT INTO  `sys_resource`( 
	`id`,
	`uri`,
	`add_time`,
	`update_time`,
	`enable`,
	`remark`,
	`name`,
	`is_delete`,
	`type`,
	`app_id`)VALUES
	(1,'/session/gettoken',now(),now(),1,'会话模块','创建会话',0,1,''),
	(2,'/session/logout',now(),now(),1,'会话模块','注销会话',0,1,''),
	(3,'/session/gethosturl',now(),now(),1,'会话模块','获取系统地址',0,1,''),
	(4,'/session/getsessionstate',now(),now(),1,'会话模块','获取会话当前状态',0,1,''),
	(5,'/backuser/userlogin',now(),now(),1,'用户模块','后台用户登录',0,1,''),
	(6,'/backuser/currentuserinfo',now(),now(),1,'用户模块','获取后台当前用户信息',0,1,''),
	(7,'/frontuser/userlogin',now(),now(),1,'用户模块','前端用户登录',0,1,''),
	(8,'/frontuser/currentuserinfo',now(),now(),1,'用户模块','前端获取当前用户信息',0,1,''),
	(9,'/frontuser/modifycurrentuserinfo',now(),now(),1,'用户模块','前端修改当前用户信息',0,1,''),
	(10,'/frontuser/modifycurrentuserloginpwd',now(),now(),1,'用户模块','前端修改当前用户登录密码',0,1,''),
	(11,'/frontuser/exist',now(),now(),1,'用户模块','校验用户是否已存在',0,1,''),
	(12,'/frontuser/regist',now(),now(),1,'用户模块','普通用户注册',0,1,''),
	(13,'/frontuser/resetpwdcheckidentity',now(),now(),1,'用户模块','找回密码——身份验证',0,1,''),
	(14,'/frontuser/resetpwdsetpwd',now(),now(),1,'用户模块','找回密码——设置新密码',0,1,''),
	(15,'/template/search',now(),now(),1,'模板模块','查询模板',0,1,''),
	(16,'/template/loadtags',now(),now(),1,'模板模块','加载筛选标签',0,1,''),
	(17,'/template/copy',now(),now(),1,'模板模块','复制模板',0,1,''),
	(18,'/template/create',now(),now(),1,'模板模块','新建模板',0,1,''),
	(19,'/template/delete',now(),now(),1,'模板模块','删除模板',0,1,''),
	(20,'/template/publish',now(),now(),1,'模板模块','提交审核',0,1,''),
	(21,'/template/load',now(),now(),1,'模板模块','加载用户模板',0,1,''),
	(22,'/template/save',now(),now(),1,'模板模块','保存用户模板',0,1,''),
	(23,'/template/verify',now(),now(),1,'模板模块','审核模板',0,1,''),
	(24,'/template/shelf',now(),now(),1,'模板模块','下架/上架模板',0,1,''),
	(25,'/template/savepublishinfo',now(),now(),1,'模板模块','保存发布设置',0,1,''),
	(26,'/template/loadpublishinfo',now(),now(),1,'模板模块','加载发布设置',0,1,''),
	(27,'/template/searchusedtemplate',now(),now(),1,'模板模块','查询用户使用过的模板',0,1,''),
	(28,'/template/preview',now(),now(),1,'模板模块','预览模版',0,1,''),
	(29,'/template/collection',now(),now(),1,'模板模块','收藏模版',0,1,''),
	(30,'/template/searchCollection',now(),now(),1,'模板模块','查询收藏',0,1,''),
	(31,'/userImg/upload',now(),now(),1,'用户资源模块','上传用户图片',0,1,''),
	(32,'/userImg/search',now(),now(),1,'用户资源模块','查询用户图片',0,1,''),
	(33,'/userImg/delete',now(),now(),1,'用户资源模块','删除用户图片',0,1,''),
	(34,'/tag/search',now(),now(),1,'标签模块','查询标签',0,1,''),
	(35,'/tag/delete',now(),now(),1,'标签模块','删除标签',0,1,''),
	(36,'/tag/setselection',now(),now(),1,'标签模块','精选标签',0,1,''),
	(37,'/tag/add',now(),now(),1,'标签模块','添加标签',0,1,''),
	(38,'/tag/order',now(),now(),1,'标签模块','标签排序',0,1,''),
	(39,'/smsvalicode/generate',now(),now(),1,'发送短信验证码','发送短信验证码',0,1,''),
	(40,'/smsvalicode/vali',now(),now(),1,'发送短信验证码','校验短信验证码',0,1,''),
	(41,'/imgvalicode/generate',now(),now(),1,'图形验证码模块','发送图形验证码',0,1,''),
	(42,'/imgvalicode/vali',now(),now(),1,'图形验证码模块','校验图形验证码',0,1,''),
	(43,'/auth/platform',now(),now(),1,'第三方登录模块','获取取第三方登陆平台',0,1,''),
	(44,'/auth/auth',now(),now(),1,'第三方登录模块','认证',0,1,''),
	(45,'/formplugin/submit',now(),now(),1,'用户表单插件模块','提交表单数据',0,1,''),
	(46,'/formplugin/search',now(),now(),1,'用户表单插件模块','查询表单数据',0,1,''),
	(47,'/special/search',now(),now(),1,'专题模块','查询专题',0,1,''),
	(48,'/special/create',now(),now(),1,'专题模块','使用模板生成专题',0,1,''),
	(49,'/special/load',now(),now(),1,'专题模块','加载用户专题',0,1,''),
	(50,'/special/copy',now(),now(),1,'专题模块','复制专题',0,1,''),
	(51,'/special/delete',now(),now(),1,'专题模块','删除专题',0,1,''),
	(52,'/special/publish',now(),now(),1,'专题模块','发布推广',0,1,''),
	(53,'/special/loadtags',now(),now(),1,'专题模块','加载筛选标签',0,1,''),
	(54,'/special/save',now(),now(),1,'专题模块','保存专题',0,1,''),
	(55,'/special/savepublishinfo',now(),now(),1,'专题模块','保存推广设置',0,1,''),
	(56,'/special/loadpublishinfo',now(),now(),1,'专题模块','加载发布设置',0,1,''),
	(57,'/special/preview',now(),now(),1,'专题模块','预览专题',0,1,''),
	(58,'/activeplugin/loadsetting',now(),now(),1,'活动插件','加载活动插件设置',0,1,''),
	(59,'/activeplugin/savesetting',now(),now(),1,'活动插件','保存活动插件设置',0,1,''),
	(60,'/activeplugin/addprizeinfo',now(),now(),1,'活动插件','填写中奖信息',0,1,''),
	(61,'/activeplugin/loadoptions',now(),now(),1,'活动插件','获取抽奖信息',0,1,''),
	(62,'/activeplugin/drawprize',now(),now(),1,'活动插件','抽奖',0,1,''),
	(63,'/activeplugin/exchangeprize',now(),now(),1,'活动插件','兑奖',0,1,''),
	(64,'/activeplugin/searchWinTrafficUser',now(),now(),1,'活动插件','查询中奖信息',0,1,''),
	(65,'/activeplugin/deleteWinTrafficUser',now(),now(),1,'活动插件','删除中奖信息',0,1,''),
	(66,'/extension/search',now(),now(),1,'推广模块','查询推广',0,1,''),
	(67,'/extension/delete',now(),now(),1,'推广模块','推广删除',0,1,''),
	(68,'/extension/disable',now(),now(),1,'推广模块','禁用推广',0,1,''),
	(69,'/extension/lookfor',now(),now(),1,'推广模块','查看推广',0,1,''),
	(70,'/extension/searchPublishSetting',now(),now(),1,'推广模块','查询发布设置',0,1,''),
	(71,'/extension/details',now(),now(),1,'推广模块','推广详情',0,1,''),
	(72,'/trafficuser/load',now(),now(),1,'流量用户模块','查看用户信息',0,1,''),
	(73,'/trafficuser/search',now(),now(),1,'流量用户模块','查询用户信息',0,1,''),
	(74,'/trafficuser/save',now(),now(),1,'流量用户模块','保存用户信息',0,1,''),
	(75,'/trafficuser/delete',now(),now(),1,'流量用户模块','删除用户信息',0,1,''),
	(76,'/material/addMaterialType',now(),now(),1,'素材管理模块','添加素材类型',0,1,''),
	(77,'/material/searchMaterialType',now(),now(),1,'素材管理模块','素材类型查询',0,1,''),
	(78,'/material/shelfMaterialType',now(),now(),1,'素材管理模块','启用/删除/禁用素材类型',0,1,''),
	(79,'/notify/count',now(),now(),1,'通知模块','获取用户通知统计',0,1,''),
	(80,'/notify/search',now(),now(),1,'通知模块','获取用户通知信息',0,1,''),
	(81,'/notify/status',now(),now(),1,'通知模块','修改通知状态',0,1,''),
	(82,'/compositeMaterial/save',now(),now(),1,'组合素材','保存',0,1,''),
	(83,'/compositeMaterial/list',now(),now(),1,'组合素材','获取列表',0,1,''),
	(84,'/compositeMaterial/info',now(),now(),1,'组合素材','获取详情',0,1,''),
	(85,'/manuscript/createmanuscript',now(),now(),1,'manuscript','createmanuscript',0,1,''),
	(86,'/manuscript/loadtags',now(),now(),1,'manuscript','loadtags',0,1,''),
	(87,'/manuscript/searchmanuscript',now(),now(),1,'manuscript','searchmanuscript',0,1,''),
	(88,'/manuscript/preview',now(),now(),1,'manuscript','preview',0,1,''),
	(401,'/member/status',now(),now(),1,'member','status',0,1,''),
	(402,'/paymentservice/placeorder',now(),now(),1,'paymentservice','placeorder',0,1,''),
	(403,'/manageuser/loaduserinfo',now(),now(),1,'manageuser','loaduserinfo',0,1,''),
	(404,'/manageuser/getuserdetail',now(),now(),1,'manageuser','getuserdetail',0,1,''),
	(405,'/paymentservice/searchorder',now(),now(),1,'paymentservice','searchorder',0,1,''),
	(301,'/paymentservice/savepayinfo',now(),now(),1,'paymentservice','savepayinfo',0,1,''),
	(302,'/paymentservice/loadpayinfo',now(),now(),1,'paymentservice','loadpayinfo',0,1,''),
	(303,'/paymentservice/savepayconfigure',now(),now(),1,'paymentservice','savepayconfigure',0,1,''),
	(304,'/paymentservice/loadpayconfigure',now(),now(),1,'paymentservice','loadpayconfigure',0,1,''),
	(305,'/bindhost/bind',now(),now(),1,'bindhost','bind',0,1,''),
	(306,'/bindhost/loaduserhost',now(),now(),1,'bindhost','loaduserhost',0,1,''),
	(200,'/userImg/searchmyselfimg',now(),now(),1,'userImg','searchmyselfimg',0,1,''),
	(201,'/special/savespecialname',now(),now(),1,'special','savespecialname',0,1,''),
	(202,'/manuscript/save',now(),now(),1,'manuscript','save',0,1,''),
	(203,'/manuscript/savemanuscriptname',now(),now(),1,'manuscript','savemanuscriptname',0,1,''),
	(204,'/manuscript/loadname',now(),now(),1,'manuscript','loadname',0,1,'');
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
DESC sys_resource;
END;
