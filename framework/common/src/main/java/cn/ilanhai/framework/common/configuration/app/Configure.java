package cn.ilanhai.framework.common.configuration.app;

import java.util.*;
import java.net.*;

import cn.ilanhai.framework.common.configuration.app.mq.MQConf;
import cn.ilanhai.framework.common.configuration.ds.DsConf;

/**
 * 定义应用配置接口
 * 
 * @author he
 *
 */
public interface Configure {

	/**
	 * 获取设置
	 * 
	 * @return
	 */
	Map getSettings();

	/**
	 * 获取应用上下文配置文件路径
	 * 
	 * @return
	 */
	String getBeansConfPath();

	/**
	 * 设置应用上下文配置文件路径
	 * 
	 * @param beansConfPath
	 */
	void setBeansConfPath(String beansConfPath);

	/**
	 * 获取可用
	 * 
	 * @return
	 */
	boolean isEnable();

	/**
	 * 设置可用
	 * 
	 * @param enable
	 */
	void setEnable(Boolean enable);

	/**
	 * 获取名称
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 设置名称
	 * 
	 * @param name
	 */
	void setName(String name);

	/**
	 * 获取jar文件路径
	 * 
	 * @return
	 */
	String getJarFileName();

	/**
	 * 设置jar文件路径
	 * 
	 * @param jarFileName
	 */
	void setJarFileName(String jarFileName);

	/**
	 * 获取包名
	 * 
	 * @return
	 */
	String getPackageName();

	/**
	 * 设置包名
	 * 
	 * @param packageName
	 */
	void setPackageName(String packageName);

	/**
	 * 获取id
	 * 
	 * @return
	 */
	String getId();

	/**
	 * 设置 id
	 * 
	 * @param id
	 */
	void setId(String id);

	void setStartupClassName(String startupClassName);

	String getStartupClassName();
	
	List<MQConf> getMQConf();
	
	List<DsConf> getDsConf();
}

