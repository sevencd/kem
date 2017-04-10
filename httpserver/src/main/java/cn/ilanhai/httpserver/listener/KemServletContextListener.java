package cn.ilanhai.httpserver.listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javassist.runtime.Cflow;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.ilanhai.framework.common.cache.Cache;
import cn.ilanhai.framework.common.configuration.cache.CacheConf;
import cn.ilanhai.framework.common.configuration.cache.CacheConfImpl;
import cn.ilanhai.framework.common.exception.CacheContainerException;
import cn.ilanhai.httpserver.modules.cache.Caches;
import cn.ilanhai.httpserver.remoteservice.AppServiceConsumer;

public class KemServletContextListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent e) {
		this.initCaches(e);
		this.initAppServiceConsumer(e);

 	}

	private void initAppServiceConsumer(ServletContextEvent e) {
		AppServiceConsumer.getInstance().init();
	}

	private void initCaches(ServletContextEvent e) {
		List<CacheConf> cacheConf = null;
		ServletContext ctx = null;
		CacheConf conf = null;
		String tmp = null;
		int iTmp = -1;
		try {
			conf = new CacheConfImpl();
			// <param-name>cacheId</param-name>
			// <param-value>redis</param-value>
			// <param-name>cacheHost</param-name>
			// <param-value>127.0.0.1</param-value>
			// <param-name>cachePort</param-name>
			// <param-value>6379</param-value>
			// <param-name>cachePassword</param-name>
			// <param-value></param-value>
			// <param-name>cacheConnectionTimeout</param-name>
			// <param-value>2048</param-value>
			// <param-name>cacheOsTimeout</param-name>
			// <param-value>2048</param-value>
			// <param-name>cacheSsl</param-name>
			// <param-value>false</param-value>
			// <param-name>cacheType</param-name>
			// <param-value>redis</param-value>
			// <param-name>cacheQuantity</param-name>
			// <param-value>16</param-value>
			ctx = e.getServletContext();
			tmp = ctx.getInitParameter("cacheId");
			if (tmp == null || tmp.length() <= 0)
				throw new Exception("redis配置编号错误");
			conf.setId(tmp);
			tmp = ctx.getInitParameter("cacheHost");
			if (tmp == null || tmp.length() <= 0)
				throw new Exception("redis配置主机地址错误");
			conf.setHost(tmp);
			tmp = ctx.getInitParameter("cachePort");
			if (tmp == null || tmp.length() <= 0)
				throw new Exception("redis配置主机端口错误");
			iTmp = Integer.valueOf(tmp);
			if (iTmp < 0x0000 || iTmp > 0xffff)
				throw new Exception("redis配置主机端口取值只能在[0~65535]");
			conf.setPort(iTmp);
			tmp = ctx.getInitParameter("cachePassword");
			conf.setPassword(tmp);
			tmp = ctx.getInitParameter("cacheConnectionTimeout");
			iTmp = Integer.valueOf(tmp);
			if (iTmp > 0)
				conf.setConnectionTimeout(iTmp);
			tmp = ctx.getInitParameter("cacheOsTimeout");
			iTmp = Integer.valueOf(tmp);
			if (iTmp > 0)
				conf.setOsTimeout(iTmp);
			tmp = ctx.getInitParameter("cacheSsl");
			conf.setSsl(false);
			if (tmp != null && tmp.equalsIgnoreCase("true"))
				conf.setSsl(true);
			tmp = ctx.getInitParameter("cacheType");
			if (tmp == null || tmp.length() <= 0)
				throw new Exception("redis配置类型错误");
			if (!tmp.equalsIgnoreCase("redis"))
				throw new Exception("redis配置类型取值只能为redis");
			conf.setType(tmp.toLowerCase());
			tmp = ctx.getInitParameter("cacheQuantity");
			if (tmp == null || tmp.length() <= 0)
				throw new Exception("redis配置db数量错误");
			iTmp = Integer.valueOf(tmp);
			if (iTmp <= 0)
				throw new Exception("redis配置db数量必需大于2");
			conf.setQuantity(iTmp);
			cacheConf = new ArrayList<CacheConf>();
			cacheConf.add(conf);
			if (!Caches.getInstance().init(cacheConf)){
				throw new Exception("初始化缓存出错");
			}
			Cache c1=Caches.getInstance().getSessionCache();
			
			System.out.println(c1.toString());
			
			Cache c2=Caches.getInstance().getRequestOverlapCache();
			
			System.out.println(c2.toString());

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {

	}

}
