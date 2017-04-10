package cn.ilanhai.framework.common.cache;

import java.util.List;

import cn.ilanhai.framework.common.configuration.cache.CacheConf;
import cn.ilanhai.framework.common.exception.CacheContainerException;

/**
 * 
 * 缓存管理器接口
 * 一个缓存管理器管理一个缓存接口
 *
 */
public interface CacheManager {

	//初始化管理器
	boolean init(CacheConf cacheConf) throws CacheContainerException;
	//获得缓存
	Cache getCache(Integer index) throws CacheContainerException;

	//清空缓存
	void cleans() throws CacheContainerException;

	//关闭
	void close() throws CacheContainerException;
}
