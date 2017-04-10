package cn.ilanhai.kem.util;

import java.util.concurrent.ConcurrentHashMap;

public class LockUtil {
	private static ConcurrentHashMap<String, Byte[]> lockerStore = new ConcurrentHashMap<String, Byte[]>();
	
	/**
	 * 单部署支持
	 * @param id
	 * @return
	 */
	public static Object getUserLock(String id) {
		lockerStore.putIfAbsent(id, new Byte[] {});
		Byte[] ret = lockerStore.get(id);
		return ret;
	}
}
