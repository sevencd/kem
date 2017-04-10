package cn.ilanhai.framework.app;

import java.net.*;

import cn.ilanhai.framework.app.args.*;
import cn.ilanhai.framework.common.exception.BlAppException;
import cn.ilanhai.framework.common.session.Session;

/**
 * 定义请求上下文接口
 * 
 * @author he
 *
 */
public interface RequestContext {

	/**
	 * 设置应用
	 * 
	 * @param application
	 */
	void setApplication(Application application);

	/**
	 * 获取应用
	 * 
	 * @return
	 */
	Application getApplication();

	/**
	 * 获取服务定位
	 * 
	 * @return
	 */
	URI getLocation();

	/**
	 * 从服务定位中获取应用id
	 * 
	 * @return
	 */
	String getApplicationIdFromLocation();

	/**
	 * 从服务定位中获取应用服务名称
	 * 
	 * @return
	 */
	String getApplicationServiceNameFromLocation();

	/**
	 * 从服务定位中获取应用服务动作名称
	 * 
	 * @return
	 */
	String getApplicationActionNameFromLocation();

	/**
	 * 设置服务定位
	 * 
	 * @param location
	 */
	void setLocation(URI location);

	/**
	 * 获取请求参数
	 * 
	 * @return
	 */
	Args getArgs();

	/**
	 * 设置请求参数
	 * 
	 * @param args
	 */
	void setArgs(Args args);

	/**
	 * 获取指定的领域对象
	 * 
	 * @param clazz
	 * @return
	 */
	<T> T getDomain(Class<T> clazz) throws BlAppException;

	/**
	 * 获取会话
	 * 
	 * @return
	 */
	Session getSession();

	String getQueryString(String key);

}
