package cn.ilanhai.framework.common;

/**
 * 定义框架的枚举
 * 
 * @author he
 *
 */
public enum CT {
	// con session [-2]
	CON_SESSION_PARA("参数出错",-2),
	CON_SESSION_DATA_ERROR("会话数据出错",-2),
	CON_SESSION_LOAD_ERROR("加载会话出错 原因:%s",-2),
	CON_SESSION_SAVE_ERROR("保存会话出错 原因:%s",-2),
	CON_SESSION_DEL_ERROR("删除会话出错 原因:%s",-2),
	CON_SESSION_EXISTS_ERROR("检查会话出错 原因:%s",-2),
	CON_SESSION_PARA_LOAD_ERROR("加载会话参数出错 原因:%s",-2),
	CON_SESSION_PARA_SAVE_ERROR("保存会话参数出错 原因:%s",-2),
	CON_SESSION_PARA_DEL_ERROR("删除会话参数出错 原因:%s",-2),
	// con conf [-3]
	// con cache [-4]
	// con ds [-5]
	// con mq [-6]
	// con app [0~-64) [-2~-10) 不能用
	APP_SUCCESS("成功", 0),
	APP_FAIL("失败", -1),
	APP_UNH_EXCEPTION("应用未处理异常",-11),
	APP_CONFIG_ERROR("应用配置错误", -12),
	APP_INIT_FAIL("应用初始化失败", -13),
	APP_REQ_LOC_ERROR("应用请求定位错误", -14),
	APP_REQ_LOC_FORMAT_ERROR("应用请求定位格式错误", -15),
	APP_CONTEXT_CONFIG_FILE_PATH_ERROR("应用上下文配置文件路径错误", -16), 
	
	// con app service [-64~128)
	APP_SER_CONTEXT_ERROR("应用服务上下文错误", -64),
	APP_SER_SER_NAME_ERROR("应用服务服务名称错误",-65),
	APP_SER_ACTION_NAME_ERROR("应用服务动作名称错误",-66),
	APP_SER_BL_OBJ_ERROR("应用服务业务逻辑对象错误", -67),
	APP_SER_BL_OBJ_NOT_IMPL_BL_INTFACE("应用服务业务逻辑对象没有实现业务接口", -68), 
	APP_SER_BL_INTFACE_NOT_CALL_METHOD("应用服务业务接口找不到调用的方法", -69),
	APP_SER_SECURITY("",-70),
	// con app bl[-128~254)
	APP_BL_UNH_EXCEPTION("应用业务逻辑未处理异常", -128), 
	APP_BL_CONTEXT_ERROR("应用业务逻辑上下文错误", -129), 
	APP_BL_SER_NAME_ERROR("业务逻辑服务名称错误", -130), 
	APP_BL_OBJ_ERROR("应用业务逻辑业务逻辑对象错误", -131), 
	APP_BL_OBJ_NOT_IMPL_BL_INTFACE("应用业务逻辑业务逻辑对象没有实现业务接口",-132),
	// con app dao[-254~512)
	APP_DAO_UNH_EXCEPTION("应用数据访问未处理异常", -254), 
	APP_DAO_SER_NAME_ERROR("应用数据访问服务名称错误", -255), 
	APP_DAO_SER_TYPE_ERROR("应用数据访问服务类型错误", -256), 
	APP_DAO_OBJ_ERROR("应用数据访问数据访问对象错误", -257), 
	APP_DAO_CONTEXT_ERROR("应用数据访问上下文错误", -258);
	
	private int val;
	private String desc;

	private CT(String desc, int val) {
		this.desc = desc;
		this.val = val;
	}

	public int getVal() {
		return this.val;
	}
	public String getDesc() {
		return this.desc;
	}

}

