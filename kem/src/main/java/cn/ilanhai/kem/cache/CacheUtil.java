package cn.ilanhai.kem.cache;

import cn.ilanhai.framework.common.cache.CacheManager;

/**
 * 缓存管理器
 * @author Nature
 *
 */
public class CacheUtil {

	private static CacheManager cacheManager=null;
	
	public static CacheManager getInstance(){
		return cacheManager;
	}
	
	public static void Init(CacheManager cacheManager){
		CacheUtil.cacheManager=cacheManager;
	}
}
