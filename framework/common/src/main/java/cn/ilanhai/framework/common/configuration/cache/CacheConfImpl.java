package cn.ilanhai.framework.common.configuration.cache;

/**
 * 缓存配置实现
 * @author Nature
 *
 */
public class CacheConfImpl implements CacheConf {
	//id，不可重复
	private String id;
	//redis服务器地址
	private String host;
	//redis端口
	private int port;
	//redis密码
	private String password;
	//连接超时时间
	private int connectionTimeout;
	//系统超时时间
	private int osTimeout;
	//是否启用ssl
	private boolean ssl;
	//缓存类型，目前仅支持redis
	private String type;
	//db数量上限
	private int quantity;
	
	private String sha1;

	public CacheConfImpl() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getOsTimeout() {
		return osTimeout;
	}

	public void setOsTimeout(int osTimeout) {
		this.osTimeout = osTimeout;
	}

	public boolean isSsl() {
		return ssl;
	}

	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getSha1() {
		return sha1;
	}

	public void setSha1(String sha1) {
		this.sha1 = sha1;
	}
}
