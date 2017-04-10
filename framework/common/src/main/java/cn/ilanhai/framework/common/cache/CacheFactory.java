package cn.ilanhai.framework.common.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.ilanhai.framework.common.cache.redis.RedisCacheManagerImpl;
import cn.ilanhai.framework.common.configuration.cache.CacheConf;
import cn.ilanhai.framework.common.exception.CacheContainerException;
import cn.ilanhai.framework.uitl.Str;

/**
 * 缓存工厂
 *
 */
public class CacheFactory {
	//缓存工厂单例实例
	private static CacheFactory instacen = null;
	//所有缓存管理器
	private Map<String, CacheManager> caches = null;
	//是否启用标志位
	private boolean flag = false;
	private List<CacheConf> cacheConf = null;

	private CacheFactory() {

	}

	public static CacheFactory getInstance() {
		if (instacen == null)
			instacen = new CacheFactory();
		return instacen;
	}

	//初始化
	public boolean init(List<CacheConf> cacheConf)  throws CacheContainerException {
		Iterator<CacheConf> iterator = null;
		CacheConf conf = null;
		CacheManager cacheManager = null;
		CacheType cacheType;
		if (this.flag)
			return true;
		if (cacheConf == null || cacheConf.size() <= 0)
			return flag;
		this.caches = new HashMap<String, CacheManager>();
		iterator = cacheConf.iterator();
		while (iterator.hasNext()) {
			conf = iterator.next();
			cacheType = CacheType.ofValue(conf.getType());
			if (cacheType == null)
				continue;
			if (cacheType == CacheType.REDIS) {
				cacheManager = new RedisCacheManagerImpl();
			}
			if (cacheManager == null)
				continue;
			if (cacheManager.init(conf)) {
				if (this.caches.containsKey(conf.getId()))
					this.caches.remove(conf.getId()).close();
				this.caches.put(conf.getId(), cacheManager);
			}
		}

		this.flag = true;
		this.cacheConf = cacheConf;
		return true;
	}

	public CacheManager getCacheManager(String id)  throws CacheContainerException {
		if (!this.flag)
			return null;
		if (Str.isNullOrEmpty(id))
			return null;
		if (!this.caches.containsKey(id))
			return null;
		return this.caches.get(id);
	}

	public void close()   throws CacheContainerException{
		if (!this.flag)
			return;
		Iterator<Entry<String, CacheManager>> iterator = null;
		Entry<String, CacheManager> entry = null;
		if (!this.flag)
			return;
		iterator = this.caches.entrySet().iterator();
		while (iterator.hasNext()) {
			entry = iterator.next();
			entry.getValue().close();
		}
		this.flag = false;
		this.caches.clear();
		this.caches = null;
	}
}
