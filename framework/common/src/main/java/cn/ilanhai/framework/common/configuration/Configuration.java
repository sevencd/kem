package cn.ilanhai.framework.common.configuration;

import java.io.*;
import java.util.*;

import cn.ilanhai.framework.common.configuration.app.Configure;
import cn.ilanhai.framework.common.configuration.app.Context;
import cn.ilanhai.framework.common.configuration.cache.CacheConf;
import cn.ilanhai.framework.common.exception.*;

/*
 *container.xml:
 *<configuration>
 *	<settings>
 *		<add key="" value="" />
 *		<add key="" value="" />	
 *	</settings>
 *	<applications>
 *		<application>
 *	 		<name>name</name>
 *			<jarFileName>package.jar</jarFile>
 *			<packageName>packageName</packageName>
 *			<enable>false|true</enable>
 *			<beansConfPath></beansConfPath>
 *			<settings>
 *				<add key="" value="" />
 *				<add key="" value="" />
 *			</settings>
 *		</application>
 *	</applications>
 *</configuration>
 */

/**
 * 定义配置接口
 * 
 * @author he
 *
 */
public interface Configuration extends Serializable {
	/**
	 * 加载配置
	 * 
	 * @throws ConfigurationContainerException
	 */
	void load() throws ConfigurationContainerException;

	/**
	 * 获取设置
	 * 
	 * @return
	 */
	Map getSettings();

	/**
	 * 获取应用配置列表
	 * 
	 * @return
	 */
	List<Configure> getApplicationConf();

	List<CacheConf> getCacheConf();

	Map getContext();

}
