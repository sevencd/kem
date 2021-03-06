CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "crawler_rule";
-- 表结构创建:
CREATE TABLE IF NOT EXISTS  `crawler_rule` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增长',
  `task_id` varchar(255) NOT NULL COMMENT '数据采集任务id',
  `type_id` int(20) DEFAULT  NULL COMMENT '行业id',
  `url` varchar(255) DEFAULT NULL COMMENT '网址',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- 表结构修改:

DESC crawler_rule;
END;
