CREATE PROCEDURE exe()
BEGIN
DECLARE ts VARCHAR(50) DEFAULT "{{dataBaseName}}";
DECLARE tn VARCHAR(50) DEFAULT "user_material";
-- DROP TABLE IF EXISTS db_ver;
CREATE TABLE IF NOT EXISTS  `user_material` (
  `material_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `material_name` varchar(255) DEFAULT NULL COMMENT '分类名称',
  `material_state`  int(11) DEFAULT NULL COMMENT '分类状态 0:启用1:禁用 2:删除',
  `createtime` datetime DEFAULT NULL,
  `terminal_type`  int(11) DEFAULT NULL COMMENT '终端类型',
  `user_id`  varchar(255) DEFAULT NULL COMMENT '用户id',
  `img_key` varchar(255) DEFAULT NULL COMMENT 'key用于前台关联',
  `material_type` int(11) DEFAULT 1 COMMENT '用于类型分类',
  PRIMARY KEY (`material_id`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8;

 IF EXISTS(SELECT * FROM information_schema.`TABLES` WHERE  TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
  			alter table user_material modify  column user_id varchar(255);
	-- IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME="file_name" AND TABLE_NAME=tn AND TABLE_SCHEMA=ts)) THEN
		-- ALTER TABLE db_ver ADD  file_name text;
	-- END IF;
	-- IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME="ver" AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		-- ALTER TABLE db_ver ADD  ver int(4);
	-- END IF;
	 IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME='material_type' AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		 ALTER TABLE user_material ADD  material_type int(11) DEFAULT 1 COMMENT '用于类型分类';
	 END IF;
	-- IF EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME='f' AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		-- ALTER TABLE db_ver DROP COLUMN f;
	-- END IF;
		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key,material_type) 
		SELECT '头部背景', '0',now(),1,0,'background_head',2 
		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '头部背景' AND img_key = 'background_head' AND terminal_type = 1);

		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key,material_type) 
		SELECT '头部背景', '0',now(),2,0,'background_head',2 
		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '头部背景' AND img_key = 'background_head' AND terminal_type = 2);
		
		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key,material_type) 
		SELECT '专题背景', '0',now(),1,0,'background_special',2
		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '专题背景' AND img_key = 'background_special' AND terminal_type = 1);
		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key,material_type) 
		SELECT '专题背景', '0',now(),2,0,'background_special',2
		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '专题背景' AND img_key = 'background_special' AND terminal_type = 2);
		
		update user_material set material_type = 2 where img_key = 'background_special';
		update user_material set material_type = 2 where img_key = 'background_head';
		
		delete FROM user_material where img_key = 'group_theme';
		delete FROM user_material where img_key = 'group_title';
		delete FROM user_material where img_key = 'group_content';
		delete FROM user_material where img_key = 'group_button';
-- 		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
--		SELECT '主题', '0',now(),1,0,'group_theme' 
--		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '主题' AND img_key = 'group_theme' AND terminal_type = 1);
--		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
--		SELECT '主题', '0',now(),2,0,'group_theme' 
--		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '主题' AND img_key = 'group_theme' AND terminal_type = 2);
-- 	
--		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
--		SELECT '标题', '0',now(),1,0,'group_title' 
--		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '标题' AND img_key = 'group_title' AND terminal_type = 1);
--		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
--		SELECT '标题', '0',now(),2,0,'group_title'
--		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '标题' AND img_key = 'group_title' AND terminal_type = 2);
-- 		
--		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
--		SELECT '内容', '0',now(),1,0,'group_content' 
--		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '内容' AND img_key = 'group_content' AND terminal_type = 1);
--		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
--		SELECT '内容', '0',now(),2,0,'group_content'
--		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '内容' AND img_key = 'group_content' AND terminal_type = 2);
--		
--		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
--		SELECT '按钮', '0',now(),1,0,'group_button' 
--		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '按钮' AND img_key = 'group_button' AND terminal_type = 1);
--		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
--		SELECT '按钮', '0',now(),2,0,'group_button'
--		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '按钮' AND img_key = 'group_button' AND terminal_type = 2);
 	
 		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
		SELECT '文字', '0',now(),1,0,'controls_text' 
		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '文字' AND img_key = 'controls_text' AND terminal_type = 1);
		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
		SELECT '文字', '0',now(),2,0,'controls_text'
		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '文字' AND img_key = 'controls_text' AND terminal_type = 2);
		
		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
		SELECT '图片', '0',now(),1,0,'controls_image' 
		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '图片' AND img_key = 'controls_image' AND terminal_type = 1);
		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
		SELECT '图片', '0',now(),2,0,'controls_image'
		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '图片' AND img_key = 'controls_image' AND terminal_type = 2);
 	
		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
		SELECT '形状', '0',now(),1,0,'controls_shape' 
		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '形状' AND img_key = 'controls_shape' AND terminal_type = 1);
		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
		SELECT '形状', '0',now(),2,0,'controls_shape'
		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '形状' AND img_key = 'controls_shape' AND terminal_type = 2);
 	
		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
		SELECT '吸附框', '0',now(),1,0,'controls_adsorbent' 
		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '吸附框' AND img_key = 'controls_adsorbent' AND terminal_type = 1);
		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
		SELECT '吸附框', '0',now(),2,0,'controls_adsorbent'
		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '吸附框' AND img_key = 'controls_adsorbent' AND terminal_type = 2);
		
		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
		SELECT '矩形框', '0',now(),1,0,'controls_rectangle' 
		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '矩形框' AND img_key = 'controls_rectangle' AND terminal_type = 1);
		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
		SELECT '矩形框', '0',now(),2,0,'controls_rectangle'
		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '矩形框' AND img_key = 'controls_rectangle' AND terminal_type = 2);
 	
		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
		SELECT '图标', '0',now(),1,0,'controls_icon' 
		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '图标' AND img_key = 'controls_icon' AND terminal_type = 1);
		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
		SELECT '图标', '0',now(),2,0,'controls_icon'
		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '图标' AND img_key = 'controls_icon' AND terminal_type = 2); 	
		
		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
		SELECT '线条', '0',now(),1,0,'controls_line' 
		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '线条' AND img_key = 'controls_line' AND terminal_type = 1);
		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
		SELECT '线条', '0',now(),2,0,'controls_line'
		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '线条' AND img_key = 'controls_line' AND terminal_type = 2); 
		
		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
		SELECT '电商', '0',now(),1,0,'controls_commerce' 
		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '电商' AND img_key = 'controls_commerce' AND terminal_type = 1);
		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
		SELECT '电商', '0',now(),2,0,'controls_commerce'
		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '电商' AND img_key = 'controls_commerce' AND terminal_type = 2); 
		
		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
		SELECT '节日', '0',now(),1,0,'controls_festival' 
		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '节日' AND img_key = 'controls_festival' AND terminal_type = 1);
		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
		SELECT '节日', '0',now(),2,0,'controls_festival'
		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '节日' AND img_key = 'controls_festival' AND terminal_type = 2); 
		
		INSERT INTO user_material (material_name, material_state, createtime, terminal_type, user_id, img_key) 
		SELECT '微信', '0',now(),2,0,'controls_wechat'
		FROM DUAL WHERE NOT EXISTS(SELECT 1 FROM user_material WHERE material_name = '微信' AND img_key = 'controls_wechat' AND terminal_type = 2); 
IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME="img_key" AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
		 ALTER TABLE user_material ADD  img_key varchar(255);
	 END IF;		
		
IF NOT EXISTS(SELECT * FROM information_schema.`COLUMNS` WHERE COLUMN_NAME="order_level" AND TABLE_NAME=tn AND TABLE_SCHEMA=ts) THEN
  			ALTER TABLE user_material ADD order_level int(11) COMMENT "数据排序等级";
		update user_material set order_level = 1 where img_key = 'controls_text';
		update user_material set order_level = 10 where img_key = 'controls_image';
		update user_material set order_level = 20 where img_key = 'controls_adsorbent';
		update user_material set order_level = 30 where img_key = 'controls_rectangle';
		update user_material set order_level = 40 where img_key = 'controls_shape';
		update user_material set order_level = 50 where img_key = 'controls_icon';
		update user_material set order_level = 60 where img_key = 'controls_line';
		update user_material set order_level = 70 where img_key = 'controls_commerce';
		update user_material set order_level = 80 where img_key = 'controls_festival';
		update user_material set order_level = 90 where img_key = 'controls_wechat';
  	END if;
		
		

 END IF;
DESC user_material;
END;
