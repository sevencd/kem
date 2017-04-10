package cn.ilanhai.framework.common.session;

import cn.ilanhai.framework.common.session.enums.SessionStateType;
import cn.ilanhai.framework.common.session.enums.SessionType;

public class SessionFactory {
	public static Session createSession(SessionManager sessionManager,
			String clientType) {
		if (sessionManager.getSessionType().getValue() == SessionType.MEMORY_SESSION
				.getValue())
			return new MemorySession(sessionManager);
		else if (sessionManager.getSessionType().getValue() == SessionType.USER_MEMORY_SESSION
				.getValue()) {
			BaseSessionState state = SessionFactory
					.createSessionState(SessionStateType.ANONYMOUS_SESSION_STATE);
			return new UserMemorySession(sessionManager, state, clientType);
		} else if (sessionManager.getSessionType().getValue() == SessionType.REDIS_SESSION
				.getValue()) {

			BaseSessionState state = SessionFactory
					.createSessionState(SessionStateType.ANONYMOUS_SESSION_STATE);
			return new RedisSession(sessionManager, state, clientType);

		} else {
			return null;
		}

	}

	public static BaseSessionState createSessionState(
			SessionStateType sessionStateType) {
		if (sessionStateType.equals(SessionStateType.ANONYMOUS_SESSION_STATE))
			return new AnonymousSessionState();
		else if (sessionStateType
				.equals(SessionStateType.FRONT_USER_LOGINED_STATE)) {
			return new FrontUserLoginedState();
		} else if (sessionStateType
				.equals(SessionStateType.BACK_USER_LOGINED_STATE)) {
			return new BackUserLoginedState();
		} else {
			return null;
		}
	}
}
