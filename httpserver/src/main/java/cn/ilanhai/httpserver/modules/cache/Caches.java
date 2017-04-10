package cn.ilanhai.httpserver.modules.cache;

import java.util.List;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.common.cache.Cache;
import cn.ilanhai.framework.common.cache.CacheFactory;
import cn.ilanhai.framework.common.cache.CacheManager;
import cn.ilanhai.framework.common.configuration.cache.CacheConf;
import cn.ilanhai.framework.common.exception.CacheContainerException;

public class Caches {
	private Logger logger = Logger.getLogger(Caches.class);
	private final String DEFAULT_CACHE_MANAGER_ID = "redis";
	private final Integer KEY_SESSION_CACHE = 0;
	private final Integer KEY_REQUEST_OVERLAP_CACHE = 1;
	private CacheFactory cacheFactory = null;
	private CacheManager cacheManager = null;
	private static Caches instance = null;
	private boolean flag = false;

	private Caches() {
		this.cacheFactory = CacheFactory.getInstance();
	}

	public static Caches getInstance() {
		if (instance == null)
			instance = new Caches();
		return instance;
	}

	public Cache getSessionCache() throws CacheContainerException {
		if (!this.flag)
			return null;
		return this.cacheManager.getCache(KEY_SESSION_CACHE);
	}

	public Cache getRequestOverlapCache() throws CacheContainerException {
		if (!this.flag)
			return null;
		return this.cacheManager.getCache(KEY_REQUEST_OVERLAP_CACHE);
	}

	public boolean init(List<CacheConf> cacheConf)
			throws CacheContainerException {
		if (this.flag)
			return true;
		if (!this.cacheFactory.init(cacheConf))
			return false;
		this.cacheManager = this.cacheFactory
				.getCacheManager(DEFAULT_CACHE_MANAGER_ID);
		this.flag = true;
		logger.info("cache init success");
		return true;
	}

	public void close() throws CacheContainerException {
		if (!this.flag)
			return;
		this.cacheFactory.close();
		this.flag = false;
	}

}
