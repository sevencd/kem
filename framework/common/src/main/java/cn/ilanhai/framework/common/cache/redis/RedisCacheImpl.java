package cn.ilanhai.framework.common.cache.redis;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import cn.ilanhai.framework.common.cache.AbstractCache;
import cn.ilanhai.framework.common.exception.CacheContainerException;
import cn.ilanhai.framework.uitl.FastJson;
import cn.ilanhai.framework.uitl.Str;

/**
 * redis缓存实现
 * 
 * @author he
 *
 */
public class RedisCacheImpl extends AbstractCache {

	private boolean flag = false;
	// db序号
	private int id = 0;
	private JedisPool jedisPool = null;

	public RedisCacheImpl(int id) {
		this.id = id;
	}

	private boolean isConnection(Jedis jedis) throws CacheContainerException {
		String tmp = null;
		try {
			if (jedis == null)
				return false;
			if (!Str.isNullOrEmpty(this.password)) {
				tmp = jedis.auth(this.password);
				if (!tmp.equalsIgnoreCase("OK"))
					return false;
			}
			tmp = jedis.ping();
			if (Str.isNullOrEmpty(tmp))
				return false;
			if (!tmp.equalsIgnoreCase("PONG"))
				return false;

			tmp = jedis.select(id);
			if (!tmp.equalsIgnoreCase("OK"))
				return false;
			return true;
		} catch (Exception e) {
			throw new CacheContainerException(e.getMessage(), e);
		}
	}

	@Override
	public boolean init() throws CacheContainerException {

		JedisPoolConfig config = null;
		if (this.flag)
			return this.flag = true;
		config = new JedisPoolConfig();
		config.setMaxTotal(0xffff);
		config.setMaxIdle(0xff);
		config.setMinIdle(0x0f);// 设置最小空闲数
		config.setMaxWaitMillis(10000);
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		// Idle时进行连接扫描
		config.setTestWhileIdle(true);
		// 表示idle object evitor两次扫描之间要sleep的毫秒数
		config.setTimeBetweenEvictionRunsMillis(30000);
		// 表示idle object evitor每次扫描的最多的对象数
		config.setNumTestsPerEvictionRun(10);
		// 表示一个对象至少停留在idle状态的最短时间，然后才能被idle object
		// evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
		config.setMinEvictableIdleTimeMillis(60000);
		this.jedisPool = new JedisPool(config, this.host, this.port);
		this.flag = true;
		return true;
	}

	@Override
	public void close() throws CacheContainerException {
		if (!this.flag)
			return;
		this.jedisPool.close();
	}

	@Override
	public boolean isExists(String key) throws CacheContainerException {
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();

			if (Str.isNullOrEmpty(key))
				return false;
			if (!this.isConnection(jedis))
				return false;
			return jedis.exists(key);
		} finally {
			if (jedis != null) {
				jedis.close();

			}
		}
	}

	public boolean isExists(byte[] key) throws CacheContainerException {
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			if (key == null || key.length <= 0)
				return false;
			if (!this.isConnection(jedis))
				return false;
			return jedis.exists(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public long del(String key) throws CacheContainerException {
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			if (Str.isNullOrEmpty(key))
				return -1;
			if (!this.isConnection(jedis))
				return -1;
			return jedis.del(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public long del(byte[] key) throws CacheContainerException {
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			if (key == null || key.length <= 0)
				return -1;
			if (!this.isConnection(jedis))
				return -1;
			return jedis.del(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public <T> T get(String key, Class<T> t) throws CacheContainerException {
		Jedis jedis = null;
		String json = null;
		try {
			jedis = this.jedisPool.getResource();
			if (Str.isNullOrEmpty(key) || t == null)
				return null;
			if (!this.isConnection(jedis))
				return null;
			if (!jedis.exists(key))
				return null;
			json = jedis.get(key);
			if (Str.isNullOrEmpty(json))
				return null;
			return this.deserialization(json, t);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public byte[] get(byte[] key) throws CacheContainerException {
		Jedis jedis = null;
		try {
			if (key == null || key.length <= 0)
				return null;
			jedis = this.jedisPool.getResource();
			if (!this.isConnection(jedis))
				return null;
			if (!jedis.exists(key))
				return null;
			return jedis.get(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public boolean set(String key, Object obj, int expiredTime) throws CacheContainerException {
		String json = null;
		Jedis jedis = null;
		try {
			if (Str.isNullOrEmpty(key) || obj == null)
				return false;
			jedis = this.jedisPool.getResource();
			if (!this.isConnection(jedis))
				return false;
			json = this.serialization(obj);
			if (Str.isNullOrEmpty(json))
				return false;
			jedis.set(key, json);
			if (expiredTime > -1)
				jedis.expire(key, expiredTime);
			return true;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public long expire(String key, int expiredTime) throws CacheContainerException {
		Jedis jedis = null;
		try {
			if (Str.isNullOrEmpty(key))
				return -1;
			jedis = this.jedisPool.getResource();
			if (!this.isConnection(jedis))
				return -1;
			return jedis.expire(key, expiredTime);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public boolean set(byte[] key, byte[] value, int expiredTime) throws CacheContainerException {
		Jedis jedis = null;
		try {
			if (key == null || key.length <= 0 || value == null || value.length <= 0)
				return false;
			jedis = this.jedisPool.getResource();
			if (!this.isConnection(jedis))
				return false;
			jedis.set(key, value);
			if (expiredTime > -1)
				jedis.expire(key, expiredTime);
			return true;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	public long expire(byte[] key, int expiredTime) throws CacheContainerException {
		Jedis jedis = null;
		try {
			if (key == null || key.length <= 0)
				return -1;
			jedis = this.jedisPool.getResource();
			if (!this.isConnection(jedis))
				return -1;
			return jedis.expire(key, expiredTime);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public void clean() throws CacheContainerException {
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			if (this.isConnection(jedis))
				jedis.flushDB();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public void cleans() throws CacheContainerException {
		Jedis jedis = null;
		try {
			jedis = this.jedisPool.getResource();
			if (this.isConnection(jedis))
				jedis.flushAll();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public long incr(String key) throws CacheContainerException {
		Jedis jedis = null;
		try {
			if (key == null || key.length() <= 0)
				return -1;
			jedis = this.jedisPool.getResource();
			if (!this.isConnection(jedis))
				return -1;
			if (!jedis.exists(key)) {
				jedis.set(key, "0");
				return 0;
			}
			return jedis.incr(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public long incr(String key, int count) throws CacheContainerException {
		Jedis jedis = null;
		try {
			if (key == null || key.length() <= 0)
				return -1;
			jedis = this.jedisPool.getResource();
			if (!this.isConnection(jedis))
				return -1;
			if (!jedis.exists(key)) {
				jedis.set(key, "0");
				return 0;
			}
			return jedis.incrBy(key, count);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> evalsha(String key, int count) throws CacheContainerException {

		Jedis jedis = null;
		try {
			if (key == null || key.length() <= 0)
				return null;
			jedis = this.jedisPool.getResource();
			if (!this.isConnection(jedis))
				return null;
			Object object = jedis.evalsha(this.sha1, 0, key, String.valueOf(count));
			String listString = String.valueOf(object);
			String[] listArray = listString.split(",");
			List<String> list = new ArrayList<String>(listArray.length);
			for (String string : listArray) {
				list.add(string);
			}
			return list;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

}
