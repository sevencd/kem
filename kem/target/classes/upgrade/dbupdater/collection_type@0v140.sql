CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "collection_type";
-- 表结构创建:
-- 删除例子
-- DROP TABLE IF EXISTS db_ver;
-- 创建例子
CREATE TABLE IF NOT EXISTS  `collection_type` (
  `type_id` int NOT NULL AUTO_INCREMENT COMMENT '类型id',
  `type_num` varchar(255) DEFAULT NULL COMMENT '类型编号用户类型查询',
  `type_name` varchar(255) DEFAULT NULL COMMENT '类型名称',
  `type_parent_num` varchar(255) DEFAULT NULL COMMENT '上级节点编号',
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;

if not exists(select type_id from collection_type where type_num="01") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('01', '装修', '', now());
end if;
if not exists(select type_id from collection_type where type_num="02") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('02', '农林牧渔', '', now());
end if;
if not exists(select type_id from collection_type where type_num="03") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('03', '批发采购', '', now());
end if;
if not exists(select type_id from collection_type where type_num="04") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('04', '企业服务', '', now());
end if;
if not exists(select type_id from collection_type where type_num="05") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('05', '百货设备', '', now());
end if;
if not exists(select type_id from collection_type where type_num="06") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('06', '房产', '', now());
end if;
if not exists(select type_id from collection_type where type_num="07") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('07', '文体教育', '', now());
end if;
if not exists(select type_id from collection_type where type_num="08") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('08', '汽车', '', now());
end if;



if not exists(select type_id from collection_type where type_num="01001") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('01001', '装修工/装修公司', '01', now());
end if;
if not exists(select type_id from collection_type where type_num="01002") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('01002', '室内设计公司/设计师', '01', now());
end if;
if not exists(select type_id from collection_type where type_num="01003") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('01003', '建材供应商', '01', now());
end if;
if not exists(select type_id from collection_type where type_num="01004") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('01004', '施工监理', '01', now());
end if;
if not exists(select type_id from collection_type where type_num="01005") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('01005', '家具/家居供应商', '01', now());
end if;
if not exists(select type_id from collection_type where type_num="01006") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('01006', '建筑装饰供应商', '01', now());
end if;


if not exists(select type_id from collection_type where type_num="02001") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('02001', '养殖／种植／水产服务商', '02', now());
end if;
if not exists(select type_id from collection_type where type_num="02002") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('02002', '果蔬／肉蛋批发商', '02', now());
end if;
if not exists(select type_id from collection_type where type_num="02003") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('02003', '农药化肥供应商', '02', now());
end if;
if not exists(select type_id from collection_type where type_num="02004") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('02004', '农用品／农机械供应商', '02', now());
end if;
if not exists(select type_id from collection_type where type_num="02005") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('02005', '其他农用品供应', '02', now());
end if;


if not exists(select type_id from collection_type where type_num="03001") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('03001', '副食零售/批发商', '03', now());
end if;
if not exists(select type_id from collection_type where type_num="03002") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('03002', '日用品零售/批发商', '03', now());
end if;
if not exists(select type_id from collection_type where type_num="03003") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('03003', '服装零售批发商', '03', now());
end if;
if not exists(select type_id from collection_type where type_num="03004") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('03004', '其他零售批发商', '03', now());
end if;

if not exists(select type_id from collection_type where type_num="04001") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('04001', '财务/财会/开业服务公司', '04', now());
end if;
if not exists(select type_id from collection_type where type_num="04002") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('04002', '法务／咨询服务公司', '04', now());
end if;
if not exists(select type_id from collection_type where type_num="04003") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('04003', '物流货运服务公司', '04', now());
end if;
if not exists(select type_id from collection_type where type_num="04004") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('04004', '企业形象/产品设计/营销服务', '04', now());
end if;
if not exists(select type_id from collection_type where type_num="04005") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('04005', '其他企业服务商', '04', now());
end if;

if not exists(select type_id from collection_type where type_num="05001") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('05001', '家居日用批发商', '05', now());
end if;
if not exists(select type_id from collection_type where type_num="05002") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('05002', '二手设备收购商', '05', now());
end if;
if not exists(select type_id from collection_type where type_num="05003") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('05003', '文体户外批发零售商', '05', now());
end if;
if not exists(select type_id from collection_type where type_num="05004") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('05004', '艺术收藏家', '05', now());
end if;
if not exists(select type_id from collection_type where type_num="05005") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('05005', '成人用品批发零售商', '05', now());
end if;
if not exists(select type_id from collection_type where type_num="05006") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('05006', '办公设备供应商', '05', now());
end if;

if not exists(select type_id from collection_type where type_num="06001") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('06001', '房产经纪人', '06', now());
end if;
if not exists(select type_id from collection_type where type_num="06002") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('06002', '租赁房东/经纪人', '06', now());
end if;


if not exists(select type_id from collection_type where type_num="07001") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('07001', '培训老师/机构', '07', now());
end if;

if not exists(select type_id from collection_type where type_num="08001") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('08001', '品牌汽车服务商', '08', now());
end if;
if not exists(select type_id from collection_type where type_num="08002") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('08002', '二手车主/商', '08', now());
end if;
if not exists(select type_id from collection_type where type_num="08003") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('08003', '汽车租赁服务商/公司', '08', now());
end if;
if not exists(select type_id from collection_type where type_num="08004") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('08004', '汽车保养服务商', '08', now());
end if;
if not exists(select type_id from collection_type where type_num="08005") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('08005', '其他汽车服务商', '08', now());
end if;
if not exists(select type_id from collection_type where type_num="08006") then
	INSERT INTO `collection_type`
	(type_num,type_name,type_parent_num,createtime)
	VALUES ('08006', '汽车用品服务商', '08', now());
end if;




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
DESC collection_type;
END;