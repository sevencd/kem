package cn.ilanhai.framework.common.session;

import cn.ilanhai.framework.common.cache.CacheManager;
import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.common.session.enums.SessionType;

public interface SessionManager {

	void register(Session session) throws SessionContainerException;

	void unRegister(Session session) throws SessionContainerException;

	Session getSession(String sessionId, String clientType)
			throws SessionContainerException;

	void putSession(Session session) throws SessionContainerException;

	void init() throws SessionContainerException;

	void close() throws SessionContainerException;

	SessionType getSessionType();

	CacheManager getCacheManager() throws SessionContainerException;

	void setCacheManager(CacheManager cacheManager)
			throws SessionContainerException;
}
