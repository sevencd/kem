package cn.ilanhai.framework.common.cache.redis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.common.cache.AbstractCache;
import cn.ilanhai.framework.common.cache.Cache;
import cn.ilanhai.framework.common.cache.CacheManager;
import cn.ilanhai.framework.common.cache.redis.RedisCacheImpl;
import cn.ilanhai.framework.common.configuration.cache.CacheConf;
import cn.ilanhai.framework.common.exception.CacheContainerException;
import cn.ilanhai.framework.uitl.Str;

/**
 * redis缓存管理器实现
 *
 */
public class RedisCacheManagerImpl implements CacheManager {
	private Logger logger = Logger.getLogger(RedisCacheManagerImpl.class);
	// private AtomicInteger index = null;
	private int maxIndex = -1;
	// 当前所有的缓存池子
	private Map<Integer, Cache> cache = null;
	// 缓存配置
	private CacheConf cacheConf = null;
	private boolean flag = false;

	// 使用配置，初始化缓存
	@Override
	public boolean init(CacheConf cacheConf) throws CacheContainerException {
		if (this.flag)
			return true;
		if (cacheConf != null) {
			this.maxIndex = cacheConf.getQuantity();
		}
		if (this.maxIndex <= 0)
			this.maxIndex = CacheConf.DEFAULT_QUANTITY;
		this.cacheConf = cacheConf;
		// index = new AtomicInteger(-1);
		this.flag = true;
		this.cache = new HashMap<Integer, Cache>();
		return true;

	}

	/**
	 * 获取缓存db
	 */
	@Override
	public Cache getCache(Integer index) throws CacheContainerException {
		Cache c = null;
		// 如果未开启，则返回null
		if (!this.flag)
			return null;
		// key为空则返回null
		if (index == null)
			return null;
		// 不存在key则创建
		if (!this.cache.containsKey(index)) {
			// 创建一个Cache
			c = this.create(index);
			// 创建成功，则从缓存中拿取
			if (c != null)
				this.cache.put(index, c);
			return c;
		}
		return this.cache.get(index);

	}

	// 创建缓存
	private Cache create(Integer index) throws CacheContainerException {
		AbstractCache abstractCache = null;
		// 如果超出最大序列，则不再生成
		if (index >= this.maxIndex)
			return null;
		// 获取新的序列
		// this.index.getAndIncrement();
		// 创建新的redis缓存
		abstractCache = new RedisCacheImpl(index);
		// 如果存在配置，则设置缓存配置
		if (this.cacheConf != null) {
			// 使用当前配置赋值缓存配置
			abstractCache.setConnectionTimeout(this.cacheConf.getConnectionTimeout());
			abstractCache.setHost(this.cacheConf.getHost());
			abstractCache.setOsTimeout(this.cacheConf.getOsTimeout());
			abstractCache.setPassword(this.cacheConf.getPassword());
			abstractCache.setPort(this.cacheConf.getPort());
			abstractCache.setSsl(this.cacheConf.isSsl());
			abstractCache.setSha1(this.cacheConf.getSha1());
		}
		logger.info(String.format("ConnectionTimeout:%s", this.cacheConf.getConnectionTimeout()));
		logger.info(String.format("Host:%s", this.cacheConf.getHost()));
		logger.info(String.format("Timeout:%s", this.cacheConf.getOsTimeout()));
		logger.info(
				String.format("Password:%s", this.cacheConf.getPassword() == null ? "" : this.cacheConf.getPassword()));
		logger.info(String.format("Port:%s", this.cacheConf.getPort()));
		logger.info(String.format("Ssl:%s", this.cacheConf.isSsl()));
		if (!abstractCache.init()) {
			// this.index.getAndDecrement();
			abstractCache = null;
			return null;
		}
		return abstractCache;
	}

	@Override
	public void close() throws CacheContainerException {
		Iterator<Entry<Integer, Cache>> iterator = null;
		Entry<Integer, Cache> entry = null;
		if (!this.flag)
			return;
		iterator = this.cache.entrySet().iterator();
		while (iterator.hasNext()) {
			entry = iterator.next();
			((AbstractCache) entry.getValue()).close();
		}
		this.flag = false;
		// this.index = null;
		this.cache.clear();
		this.cache = null;

	}

	@Override
	public void cleans() throws CacheContainerException {
		if (!this.flag)
			return;
		Iterator<Entry<Integer, Cache>> iterator = null;
		Entry<Integer, Cache> entry = null;
		if (!this.flag)
			return;
		iterator = this.cache.entrySet().iterator();
		while (iterator.hasNext()) {
			entry = iterator.next();
			((AbstractCache) entry.getValue()).cleans();
			break;
		}

	}

}
