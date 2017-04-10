package cn.ilanhai.framework.common.configuration.cache;

/**
 * 缓存的配置接口
 *
 */
public interface CacheConf {
	int DEFAULT_QUANTITY = 0X10;

	String getId();

	void setId(String id);

	String getHost();

	void setHost(String host);

	int getPort();

	void setPort(int port);

	String getPassword();

	void setPassword(String password);

	int getConnectionTimeout();

	void setConnectionTimeout(int connectionTimeout);

	int getOsTimeout();

	void setOsTimeout(int osTimeout);

	boolean isSsl();

	void setSsl(boolean ssl);

	String getType();

	void setType(String type);

	int getQuantity();

	void setQuantity(int quantity);

	String getSha1();

	void setSha1(String sha1);
}
