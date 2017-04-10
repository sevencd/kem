package cn.ilanhai.framework.common.session;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.ilanhai.framework.common.cache.Cache;
import cn.ilanhai.framework.common.cache.CacheManager;
import cn.ilanhai.framework.common.cache.redis.RedisCacheImpl;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.common.session.enums.SessionType;
import cn.ilanhai.framework.uitl.Serialize;
import cn.ilanhai.framework.uitl.Str;

public class SessionManagerImpl implements SessionManager {
	private static Logger logger = Logger.getLogger(SessionManagerImpl.class);

	private Map<String, Session> sessions = null;
	private static SessionManagerImpl instance = null;
	private SessionType sessionType = null;

	private SessionManagerImpl() {
		this.sessions = new HashMap<String, Session>();
		this.sessionType = SessionType.USER_MEMORY_SESSION;
	}

	public static SessionManager getInstance() {
		if (instance == null)
			instance = new SessionManagerImpl();
		return instance;
	}

	public Session getSession(String sessionId, String clientType)
			throws SessionContainerException {
		Session session = null;
		if (sessionId != null && sessionId.length() > 0) {
			if (this.sessions.containsKey(sessionId))
				session = this.sessions.get(sessionId);
		}
		if (session != null) {
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
		if (sessions.containsKey(session.getSessionId())) {
			sessions.remove(session.getSessionId());
		}
		sessions.put(session.getSessionId(), session);
	}

	public void unRegister(Session session) throws SessionContainerException {
		if (sessions.containsKey(session.getSessionId())) {
			sessions.remove(session.getSessionId());
		}
	}

	public void init() {
		logger.info("start inital session manager");
		logger.info("end inital session manager");
	}

	public SessionType getSessionType() {
		return this.sessionType;
	}

	public void close() throws SessionContainerException {

	}

	public CacheManager getCacheManager() throws SessionContainerException {
		return null;
	}

	public void setCacheManager(CacheManager cacheManager)
			throws SessionContainerException {
	}

	public void putSession(Session session) throws SessionContainerException {

	}
}
