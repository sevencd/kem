package cn.ilanhai.framework.common.cache;

import cn.ilanhai.framework.common.exception.CacheContainerException;
import cn.ilanhai.framework.uitl.FastJson;

/**
 * 缓存抽象类
 * @author Nature
 *
 */
public abstract class AbstractCache implements Cache {
	//默认参数
	private final String DEFAULT_HOST = "127.0.0.1";
	private final int DEFAULT_PORT = 6379;
	private final int DEFAULT_CONNECTION_TIMIEOUT = 0X0000FFFF;
	private final int DEFAULT_OS_TIMIEOUT = 0X0000FFFF;
	private final Boolean DEFAULT_SSL = false;
	private final String DEFAULT_PASSWORD = "";

	//主机
	protected String host = DEFAULT_HOST;
	//端口
	protected int port = DEFAULT_PORT;
	//连接超时时间
	protected int connectionTimeout = DEFAULT_CONNECTION_TIMIEOUT;
	//系统超时时间
	protected int osTimeout = DEFAULT_OS_TIMIEOUT;
	//是否开启ssl
	protected boolean ssl = DEFAULT_SSL;
	//密码
	protected String password = DEFAULT_PASSWORD;
	
	protected String sha1 = "";

	protected AbstractCache() {

	}

	//初始化方法
	public abstract boolean init() throws CacheContainerException;

	//关闭缓存连接
	public abstract void close() throws CacheContainerException;

	/**
	 * 序列化成json字符串
	 * 如果是String，则不进行序列化
	 * @param obj
	 * @return
	 */
	protected String serialization(Object obj) {
		if(obj instanceof String){
			return (String) obj;
		}
		return FastJson.bean2Json(obj);
	}
	/**
	 * 从json反序列化成对象
	 * 如果class是String，则不进行反序列化
	 * @param json
	 * @param t
	 * @return
	 */
	protected <T> T deserialization(String json, Class<T> t) {
		if(t.equals(String.class)){
			return (T) json;
		}
		return FastJson.json2Bean(json, t);
	}

	//键值是否存在
	@Override
	public boolean isExists(String key) throws CacheContainerException {
		return false;
	}

	//获取键值
	@Override
	public <T> T get(String key, Class<T> t) throws CacheContainerException {
		return null;
	}

	//设置键值
	@Override
	public boolean set(String key, Object obj, int expiredTime)
			throws CacheContainerException {
		return false;
	}

	//清空
	@Override
	public void clean() throws CacheContainerException {

	}
	//清空
	public abstract void cleans() throws CacheContainerException;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSha1() {
		return sha1;
	}

	public void setSha1(String sha1) {
		this.sha1 = sha1;
	}
}
