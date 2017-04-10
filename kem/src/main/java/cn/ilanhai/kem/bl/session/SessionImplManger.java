package cn.ilanhai.kem.bl.session;

import cn.ilanhai.framework.app.RequestContext;
import cn.ilanhai.framework.common.session.AnonymousSessionState;
import cn.ilanhai.framework.common.session.BackUserLoginedState;
import cn.ilanhai.framework.common.session.BaseSessionState;
import cn.ilanhai.framework.common.session.FrontUserLoginedState;
import cn.ilanhai.framework.common.session.Session;
import cn.ilanhai.framework.common.session.SessionFactory;
import cn.ilanhai.framework.common.session.enums.SessionStateType;

public class SessionImplManger {
	public static void logout(RequestContext context) {
		Session session = context.getSession();
		BaseSessionState state = SessionFactory.createSessionState(SessionStateType.ANONYMOUS_SESSION_STATE);
		session.verify(state);
	}

	public static Integer getSessionState(BaseSessionState currentState) {
		if (currentState instanceof AnonymousSessionState) {
			return 0;
		} else if (currentState instanceof FrontUserLoginedState) {
			return 1;
		} else if (currentState instanceof BackUserLoginedState) {
			return 2;
		} else {
			return -1;
		}
	}
}
