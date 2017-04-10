package cn.ilanhai.framework.common.session;

import cn.ilanhai.framework.common.session.enums.SessionStateType;
 
/**
 * 匿名会话
 * 
 * @author Nature
 *
 */
public class AnonymousSessionState extends BaseSessionState {

	private SessionStateType sessionStateType = SessionStateType.ANONYMOUS_SESSION_STATE;

	@Override
	public SessionStateType getSessionStateType() {
		return sessionStateType;
	}

	@Override
	public boolean verify(String clientType, BaseSessionState currentState) {
		return false;
	}

}
