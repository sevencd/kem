package cn.ilanhai.framework.common.cache;

import java.util.List;

import cn.ilanhai.framework.common.exception.CacheContainerException;

/**
 * 缓存接口
 * 
 * @author he
 *
 */
public interface Cache {

	/**
	 * 检查key是否存在
	 * 
	 * @param key
	 * @return
	 */
	boolean isExists(String key) throws CacheContainerException;

	/**
	 * 从缓存中获取数据
	 * 
	 * @param key
	 * @param t
	 * @return
	 */
	<T> T get(String key, Class<T> t) throws CacheContainerException;

	/**
	 * 将数据保存在缓存中
	 * 
	 * @param key
	 * @param obj
	 * @param expiredTime
	 *            -1永不过期 (单位:秒)
	 * @return
	 */
	boolean set(String key, Object obj, int expiredTime) throws CacheContainerException;

	/**
	 * 自增
	 * 
	 * @return
	 * @throws CacheContainerException
	 */
	long incr(String key) throws CacheContainerException;

	/**
	 * 步长自增
	 * 
	 * @return
	 */
	long incr(String key, int count) throws CacheContainerException;

	/**
	 * 清空缓存
	 */
	void clean() throws CacheContainerException;

	List<String> evalsha(String key, int count) throws CacheContainerException;
}
