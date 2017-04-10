package cn.ilanhai.framework.container;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.app.*;
import cn.ilanhai.framework.common.cache.CacheFactory;
import cn.ilanhai.framework.common.cache.CacheManager;
import cn.ilanhai.framework.common.configuration.Configuration;
import cn.ilanhai.framework.common.configuration.ConfigurationImpl;
import cn.ilanhai.framework.common.configuration.app.Configure;
import cn.ilanhai.framework.common.exception.*;
import cn.ilanhai.framework.common.session.RedisSessionManagerImpl;
import cn.ilanhai.framework.common.session.SessionManager;
import cn.ilanhai.framework.common.session.SessionManagerImpl;
import cn.ilanhai.framework.uitl.Str;

/**
 * 定义应用管理
 * 
 * @author he
 *
 */
public class ApplicationManagerImpl implements ApplicationManager {
	private final String DEFAULT_CACHE_MANAGER_ID = "redis";
	private static Logger logger = Logger
			.getLogger(ApplicationManagerImpl.class);

	private static ApplicationManagerImpl applicationManagerImpl = null;
	private boolean flag = false;
	private Map<String, Application> applications = null;
	private Configuration configuration = null;
	private SessionManager sessionManager = null;
	private CacheFactory cacheFactory = null;

	private ApplicationManagerImpl() {
		this.applications = new HashMap<String, Application>();
		this.configuration = ConfigurationImpl.getInstance();
		this.sessionManager = RedisSessionManagerImpl.getInstance();
		this.cacheFactory = CacheFactory.getInstance();
	}

	public static ApplicationManager getInstance() {
		if (applicationManagerImpl == null)
			applicationManagerImpl = new ApplicationManagerImpl();
		return applicationManagerImpl;
	}

	public void init() throws ContainerException {
		logger.info("start initial appalication manager");
		CacheManager cacheManager = null;
		try {
			this.configuration.load();
			this.cacheFactory.init(this.configuration.getCacheConf());
			cacheManager = this.cacheFactory
					.getCacheManager(DEFAULT_CACHE_MANAGER_ID);
			this.sessionManager.setCacheManager(cacheManager);
			this.sessionManager.init();

		} catch (ConfigurationContainerException e) {
			throw e;
		} catch (Exception e) {
			throw new ContainerException(e);
		}
		logger.info("end initial appalication manager");
	}

	public Map<String, Application> getApplications() {
		return this.applications;
	}

	public Application getApplication(String id) {
		if (!this.applications.containsKey(id))
			return null;
		return this.applications.get(id);
	}

	public void start() throws ContainerException {
		logger.info("begin start application mananger");
		ApplicationImpl application = null;
		List<Configure> cfg = null;
		Iterator<Configure> iter = null;
		Configure configure = null;
		String startupClassName = null;
		ClassLoader classLoader = null;
		Class<?> customerApplication = null;
		Constructor<?> constructor = null;
		CacheManager cacheManager = null;
		try {
			if (this.flag)
				return;
			cfg = this.configuration.getApplicationConf();
			if (cfg == null || cfg.size() <= 0)
				throw new ContainerException("");
			cacheManager = this.cacheFactory
					.getCacheManager(DEFAULT_CACHE_MANAGER_ID);
			iter = cfg.iterator();
			while (iter.hasNext()) {
				flag = false;
				configure = iter.next();
				if (!configure.isEnable())
					continue;
				startupClassName = configure.getStartupClassName();
				if (Str.isNullOrEmpty(startupClassName)) {
					application = new ApplicationImpl(configure);
				} else {
					classLoader = Thread.currentThread()
							.getContextClassLoader();
					customerApplication = classLoader
							.loadClass(startupClassName);
					if (customerApplication == null) {
						logger.info(String.format("load %s class error",
								startupClassName));
						return;
					}
					if (!this.checkStartupClass(customerApplication)) {
						logger.info("not implements Application interfaces or extends ApplicationImpl");
						return;
					}
					constructor = customerApplication
							.getConstructor(Configure.class);
					if (constructor == null) {
						logger.info("not specified constructor");
						return;
					}
					application = (ApplicationImpl) constructor
							.newInstance(configure);
				}
				application.setCacheManager(cacheManager);
				application.setSessionManager(this.sessionManager);
				application.init();

				logger.info("start application " + configure.getName());
				this.applications.put(configure.getId(), application);
			}
			this.flag = true;

		} catch (AppException e) {
			throw new ContainerException(e);
		} catch (ContainerException e) {
			throw e;
		} catch (Exception e) {
			throw new ContainerException(e);
		}
		logger.info("end start application mananger");
	}

	private boolean checkStartupClass(Class<?> customerApplication) {

		if (customerApplication == null)
			return false;
		Class<?>[] interfaces = null;
		Class<?> superclass = null;
		superclass = customerApplication.getSuperclass();
		interfaces = customerApplication.getInterfaces();
		if (superclass == null
				&& (interfaces == null || interfaces.length <= 0))
			return false;
		if (superclass != ApplicationImpl.class)
			if (!checkStartupClass(interfaces, Application.class))
				return false;
		return true;
	}

	private boolean checkStartupClass(Class<?>[] interfaces,
			Class<Application> c) {
		boolean flag = false;
		if (interfaces == null || interfaces.length <= 0)
			return false;
		if (c == null)
			return false;
		for (int i = 0; i < interfaces.length; i++) {
			if (interfaces[i] == c) {
				this.flag = true;
				break;
			}
		}
		return flag;
	}

	public void close() throws ContainerException {
		Iterator<Entry<String, Application>> iterator = null;
		Entry<String, Application> entry = null;
		try {
			iterator = this.applications.entrySet().iterator();
			while (iterator.hasNext()) {
				entry = iterator.next();
				entry.getValue().close();
			}
		} catch (AppException e) {
			throw e;
		}
	}

	public Configuration getConfiguration() {

		return this.configuration;
	}

}
