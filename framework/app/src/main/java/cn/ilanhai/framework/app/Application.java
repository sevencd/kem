package cn.ilanhai.framework.app;

import java.util.*;
import java.net.*;

import org.springframework.context.*;

import cn.ilanhai.framework.app.domain.*;
import cn.ilanhai.framework.common.cache.CacheManager;
import cn.ilanhai.framework.common.configuration.app.Configure;
import cn.ilanhai.framework.common.ds.DsManager;
import cn.ilanhai.framework.common.exception.*;

/**
 * 定义应用接口
 * 
 * @author he
 *
 */
public interface Application {

	Response service(URI location, Entity entity) throws  ContainerException;

	String serviceJSON(URI location, String json);

	String serviceXML(URI location, String json);

	/**
	 * 获取应用的上不文
	 * 
	 * @return
	 */
	ApplicationContext getApplicationContext();

	/**
	 * 初始化应用上不文
	 */
	void init() throws AppException;

	/**
	 * 获取应用id
	 * 
	 * @return
	 */
	String getId();

	/**
	 * 设置应用id
	 * 
	 * @param id
	 */
	void setId(String id);

	/**
	 * 获取应用名称
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 设置广应用名称
	 * 
	 * @param name
	 */
	void setName(String name);

	/**
	 * 获取应用配置
	 * 
	 * @return
	 */
	Configure getConfigure();

	/**
	 * 设置应用配置
	 * 
	 * @param configure
	 */
	void setConfigure(Configure configure);

	void close() throws AppException;



	CacheManager getCacheManager();


	DsManager getDs();

}
