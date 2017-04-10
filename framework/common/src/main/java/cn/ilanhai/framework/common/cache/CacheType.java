package cn.ilanhai.framework.common.cache;

/**
 * 缓存类型
 *
 */
public enum CacheType {
	REDIS("redis");
	private String value = "";

	private CacheType(String value) {
		this.value = value;
	}

	public static CacheType ofValue(String value) {
		if (value == null)
			return null;
		if (value.equalsIgnoreCase(REDIS.toString())) {
			return REDIS;
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return this.value;
	}

}
