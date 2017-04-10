package cn.ilanhai.framework.container;

import cn.ilanhai.framework.app.*;
import cn.ilanhai.framework.common.cache.CacheManager;
import cn.ilanhai.framework.common.configuration.Configuration;
import cn.ilanhai.framework.common.exception.*;

import java.util.*;

/**
 * 目录结构:
 * -当前运行目录
 * --conf
 * ---container.xml
 * ---apps
 * --libs
 * ---apps
 * 
 */

/**
 * 定义应用管理接口
 * 
 * @author he
 *
 */
public interface ApplicationManager {

	/**
	 * 初始化应用
	 * 
	 * @throws ContainerException
	 */
	void init() throws ContainerException;

	/**
	 * 获取应用
	 * 
	 * @return
	 */
	Map<String, Application> getApplications();

	/**
	 * 通过应用id获取应用
	 * 
	 * @param id
	 * @return
	 * @throws ContainerException
	 */
	Application getApplication(String id) throws ContainerException;

	/**
	 * 开启应用
	 * 
	 * @throws ContainerException
	 */
	void start() throws ContainerException;

	/**
	 * 关闭应用
	 * 
	 * @throws ContainerException
	 */
	void close() throws ContainerException;

	/**
	 * 获取配置
	 * 
	 * @return
	 */
	Configuration getConfiguration();


}
