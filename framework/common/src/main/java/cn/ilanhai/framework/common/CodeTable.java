package cn.ilanhai.framework.common;

/**
 * 定义框架的枚举
 * 
 * @author he
 *
 */
public enum CodeTable {

	//HTTP
	// app
	APP_UNHANDLED_EXCEPTION("未知处理异常", 0), 
	APP_SUCCESS("成功", 0), 
	APP_FAIL("失败", -1),
	APP_CONFIGURE_NOT_NULL("应用配置错误", 0),
	APP_CONFIGURE_BEANS_CONF_PATH("应用配置中组件配置路径错误", 0), 
	APP_INIT_ERROR("应用初始化失败", 0),
	APP_LOCATION_NOT_NULL("服务定位错误", 0), 
	APP_LOCATION_FORMAT_ERROR("服务定位格式错误", 0),
	// service
	APP_SERVICE_REQUEST_CONTEXT_NOT_NULL("请求上下文错误", 0), 
	APP_SERVICE_SERVICENAME_NOT_NULLOREMPTY("服务名称错误", -2), 
	APP_SERVICE_ACTIONNAME_NOT_NULLOREMPTY("动作名称错误", -2), 
	APP_SERVICE_BL_OBJECT_NOT_NULL("业务逻辑对象错误", -3), 
	APP_SERVICE_BL_OBJECT_NOT_IMPL_BL_INTFACE("业务逻辑对象没有实现业务接口", -4), 
	APP_SERVICE_BL_INTFACE_NOT_CALL_METHOD("业务接打找不到调用的方法", -5),
	// bl
	APP_BL_UNHANDLED_EXCEPTION("未知处理异常", -5), 
	APP_BL_REQUEST_CONTEXT_NOT_NULL("请求上下文错误", 0), 
	APP_BL_SERVICENAME_NOT_NULLOREMPTY("服务名称错误", -2), 
	APP_BL_OBJECT_NOT_NULL("业务逻辑对象错误", -3), 
	APP_BL_OBJECT_NOT_IMPL_BL_INTFACE("业务逻辑对象没有实现业务接口", -4),
	//dao
	APP_DAO_UNHANDLED_EXCEPTION("未知处理异常", -5), 
	APP_DAO_SERVICENAME_NOT_NULLOREMPTY("服务名称错误", -2), 
	APP_DAO_BEANNAME_NOT_NULLOREMPTY("服务名称错误", -2), 
	APP_DAO_OBJECT_NOT_NULL("业务逻辑对象错误", -3), 
	APP_DAO_REQUEST_CONTEXT_NOT_NULL("请求上下文错误", 0);
	private int value;
	private String name;

	private CodeTable(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
