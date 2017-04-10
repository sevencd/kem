package cn.ilanhai.framework.common.session;

import java.util.Calendar;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.common.CT;
import cn.ilanhai.framework.common.cache.Cache;
import cn.ilanhai.framework.common.cache.CacheManager;
import cn.ilanhai.framework.common.cache.redis.RedisCacheImpl;
import cn.ilanhai.framework.common.exception.CacheContainerException;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.common.session.enums.SessionType;
import cn.ilanhai.framework.uitl.Serialize;
import cn.ilanhai.framework.uitl.Str;

public class RedisSessionManagerImpl implements SessionManager {
	private final Integer KEY_SESSION_CACHE = 0;
	private static Logger logger = Logger
			.getLogger(RedisSessionManagerImpl.class);
	private static RedisSessionManagerImpl instance = null;
	private SessionType sessionType = null;
	private CacheManager cacheManager = null;
	private RedisCacheImpl cache = null;

	private RedisSessionManagerImpl() {
		this.sessionType = SessionType.REDIS_SESSION;
	}

	public static SessionManager getInstance() {
		if (instance == null)
			instance = new RedisSessionManagerImpl();
		return instance;
	}

	public Session getSession(String sessionId, String clientType)
			throws SessionContainerException {
		Session session = null;
		if (sessionId != null && sessionId.length() > 0) {
			if (this.isExistsSession(sessionId))
				session = this.loadSession(sessionId);

		}
		if (session != null) {
			((AbstractSession) session).setSessionManager(this);
			if (!session.isExpire()) {
				session.fresh();
				return session;
			} else {
				session.abandon();
			}
		}
		session = SessionFactory.createSession(this, clientType);
		this.register(session);
		return session;
	}

	public void register(Session session) throws SessionContainerException {
		if (session == null)
			return;
		this.saveSession(session);
	}

	public void unRegister(Session session) throws SessionContainerException {
		if (session == null)
			return;
		this.deleteSession(session);
	}

	public void init() throws SessionContainerException {
		logger.info("start inital session manager");
		logger.info("end inital session manager");

	}

	public SessionType getSessionType() {
		return this.sessionType;
	}

	public void close() throws SessionContainerException {

	}

	public CacheManager getCacheManager() throws SessionContainerException {
		return cacheManager;
	}

	public void setCacheManager(CacheManager cacheManager)
			throws SessionContainerException {
		try {
			this.cacheManager = cacheManager;
			Cache temp = null;
			temp = this.cacheManager.getCache(KEY_SESSION_CACHE);
			if (temp instanceof RedisCacheImpl)
				cache = (RedisCacheImpl) temp;
		} catch (CacheContainerException e) {
			throw new SessionContainerException(e);
		}
	}

	/**
	 * 过期时间
	 * 
	 * @return
	 */
	private int getTime() {
		return 24 * 60 * 60;
	}

	private boolean save(byte[] key, byte[] value)
			throws CacheContainerException {
		try {
			if (key == null || key.length <= 0)
				return false;
			if (value == null || value.length <= 0)
				return false;
			if (cache == null)
				return false;
			return cache.set(key, value, this.getTime());
		} catch (CacheContainerException e) {
			throw e;
		}
	}

	public boolean saveSession(Session session)
			throws SessionContainerException {
		CT ct;
		try {
			if (session == null) {
				ct = CT.CON_SESSION_PARA;
				throw new SessionContainerException(ct.getVal(), ct.getDesc());
			}
			return this.save(session.getSessionId().getBytes(),
					Serialize.serialize(session));
		} catch (CacheContainerException e) {
			ct = CT.CON_SESSION_SAVE_ERROR;
			throw new SessionContainerException(ct.getVal(), String.format(
					ct.getDesc(), e.getMessage()));
		} catch (SessionContainerException e) {
			throw e;
		} catch (Exception e) {
			ct = CT.CON_SESSION_SAVE_ERROR;
			throw new SessionContainerException(ct.getVal(), String.format(
					ct.getDesc(), e.getMessage()));
		}
	}

	public boolean saveSessionParameter(Session session, String key,
			Object value) throws SessionContainerException {
		CT ct;
		try {
			if (session == null || key == null || key.length() <= 0
					|| value == null) {
				ct = CT.CON_SESSION_PARA;
				throw new SessionContainerException(ct.getVal(), ct.getDesc());
			}
			return this.save(String.format("%s:%s", session.getSessionId(), key)
					.getBytes(), Serialize.serialize(value));
		} catch (CacheContainerException e) {
			ct = CT.CON_SESSION_PARA_SAVE_ERROR;
			throw new SessionContainerException(ct.getVal(), String.format(
					ct.getDesc(), e.getMessage()));
		} catch (SessionContainerException e) {
			throw e;
		} catch (Exception e) {
			ct = CT.CON_SESSION_PARA_SAVE_ERROR;
			throw new SessionContainerException(ct.getVal(), String.format(
					ct.getDesc(), e.getMessage()));
		}
	}

	private boolean delete(byte[] key) throws CacheContainerException {

		try {
			if (key == null || key.length <= 0)
				return false;
			if (cache == null)
				return false;
			return cache.del(key) > 0;
		} catch (CacheContainerException e) {
			throw e;
		}
	}

	public boolean deleteSession(Session session)
			throws SessionContainerException {
		CT ct;
		try {
			if (session == null) {
				ct = CT.CON_SESSION_PARA;
				throw new SessionContainerException(ct.getVal(), ct.getDesc());
			}
			return this.delete(session.getSessionId().getBytes());
		} catch (CacheContainerException e) {
			ct = CT.CON_SESSION_DEL_ERROR;
			throw new SessionContainerException(ct.getVal(), String.format(
					ct.getDesc(), e.getMessage()));
		} catch (SessionContainerException e) {
			throw e;
		} catch (Exception e) {
			ct = CT.CON_SESSION_DEL_ERROR;
			throw new SessionContainerException(ct.getVal(), String.format(
					ct.getDesc(), e.getMessage()));
		}
	}

	public boolean deleteSessionParameter(Session session, String key)
			throws SessionContainerException {
		CT ct;
		try {
			if (session == null || key == null || key.length() <= 0) {
				ct = CT.CON_SESSION_PARA;
				throw new SessionContainerException(ct.getVal(), ct.getDesc());
			}
			return this.delete(String.format("%s:%s", session.getSessionId(),
					key).getBytes());
		} catch (CacheContainerException e) {
			ct = CT.CON_SESSION_PARA_DEL_ERROR;
			throw new SessionContainerException(ct.getVal(), String.format(
					ct.getDesc(), e.getMessage()));
		} catch (SessionContainerException e) {
			throw e;
		} catch (Exception e) {
			ct = CT.CON_SESSION_PARA_DEL_ERROR;
			throw new SessionContainerException(ct.getVal(), String.format(
					ct.getDesc(), e.getMessage()));
		}
	}

	public byte[] load(byte[] key) throws CacheContainerException {
		try {
			if (key == null || key.length <= 0)
				return null;
			if (cache == null)
				return null;
			cache.expire(key, this.getTime());
			return cache.get(key);
		} catch (CacheContainerException e) {
			throw e;
		}
	}

	public Session loadSession(String sessionId)
			throws SessionContainerException {
		byte[] bytes = null;
		CT ct;
		try {

			bytes = this.load(sessionId.getBytes());
			if (Str.isNullOrEmpty(sessionId)) {
				ct = CT.CON_SESSION_PARA;
				throw new SessionContainerException(ct.getVal(), ct.getDesc());
			}
			if (bytes == null || bytes.length <= 0) {
				ct = CT.CON_SESSION_DATA_ERROR;
				throw new SessionContainerException(ct.getVal(), ct.getDesc());
			}
			return (Session) Serialize.unserialize(bytes);
		} catch (CacheContainerException e) {
			ct = CT.CON_SESSION_LOAD_ERROR;
			throw new SessionContainerException(ct.getVal(), String.format(
					ct.getDesc(), e.getMessage()));
		} catch (SessionContainerException e) {
			throw e;
		} catch (Exception e) {
			ct = CT.CON_SESSION_LOAD_ERROR;
			throw new SessionContainerException(ct.getVal(), String.format(
					ct.getDesc(), e.getMessage()));
		}
	}

	public Object loadSessionParameter(Session session, String key)
			throws SessionContainerException {
		byte[] bytes = null;
		CT ct;
		try {
			if (session == null || key == null || key.length() <= 0) {
				ct = CT.CON_SESSION_PARA;
				throw new SessionContainerException(ct.getVal(), ct.getDesc());
			}
			bytes = this.load(String
					.format("%s:%s", session.getSessionId(), key).getBytes());
			return Serialize.unserialize(bytes);
		} catch (CacheContainerException e) {
			ct = CT.CON_SESSION_PARA_LOAD_ERROR;
			throw new SessionContainerException(ct.getVal(), String.format(
					ct.getDesc(), e.getMessage()));
		} catch (SessionContainerException e) {
			throw e;
		} catch (Exception e) {
			ct = CT.CON_SESSION_PARA_LOAD_ERROR;
			throw new SessionContainerException(ct.getVal(), String.format(
					ct.getDesc(), e.getMessage()));
		}
	}

	public boolean isExists(byte[] key) throws CacheContainerException {
		try {
			if (key == null || key.length <= 0)
				return false;
			if (cache == null)
				return false;
			return cache.isExists(key);
		} catch (CacheContainerException e) {
			throw e;
		}
	}

	public boolean isExistsSession(String sessionId)
			throws SessionContainerException {
		CT ct;
		try {
			if (Str.isNullOrEmpty(sessionId))
				return false;
			return this.isExists(sessionId.getBytes());
		} catch (CacheContainerException e) {
			ct = CT.CON_SESSION_EXISTS_ERROR;
			throw new SessionContainerException(ct.getVal(), String.format(
					ct.getDesc(), e.getMessage()));
		} catch (Exception e) {
			ct = CT.CON_SESSION_EXISTS_ERROR;
			throw new SessionContainerException(ct.getVal(), String.format(
					ct.getDesc(), e.getMessage()));
		}
	}

	public void putSession(Session session) throws SessionContainerException {
		if (session == null)
			return;
		this.saveSession(session);

	}
}
