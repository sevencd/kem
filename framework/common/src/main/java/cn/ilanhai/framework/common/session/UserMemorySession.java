package cn.ilanhai.framework.common.session;

import cn.ilanhai.framework.common.session.enums.SessionStateType;

public class UserMemorySession extends MemorySession {
	// 用户状态
	public static final String STATE_ANONYMOUS = "anonymous_state";
	public static final String STATE_FRONTUSER_LOGINED = "frontuser_logined_state";
	public static final String STATE_BACKUSER_LOGINED = "backuser_logined_state";

	// session键值
//	public static final String KEY_USERID = "userid";
//	public static final String KEY_USERTYPE = "usertype";

	/**
	 * 会话状态
	 */
	private BaseSessionState sessionState;
	/**
	 * 会话客户端类型，依据约定而定，可空
	 */
	private String clientType;

	public UserMemorySession(SessionManager sessionManager,
			BaseSessionState state, String clientType) {
		super(sessionManager);
		this.sessionState = state;
		this.clientType = clientType;

	}

	/**
	 * 如果会话已过期，则返回false，否则，返回true,如果已过期则自动置为匿名会话
	 */
	public boolean checkTimeOut() {
		if (this.sessionState.getSessionStateType().equals(
				SessionStateType.ANONYMOUS_SESSION_STATE)) {
			return true;
		} else {
			if (this.sessionState.isTimeout()) {
				BaseSessionState sessionState = SessionFactory
						.createSessionState(SessionStateType.ANONYMOUS_SESSION_STATE);
				this.sessionState = sessionState;
				return false;
			} else {
				return true;
			}
		}
	}

	/**
	 * 检查会话是否是指定状态 需要使用state的自身的verify方法，判断是否可以通过verify，
	 * 无法通过则返回false，否则返回true，并替换当前sessionState为传入sessionState
	 * 
	 * @param state
	 *            想要判断的状态
	 * @return
	 */
	public boolean verify(BaseSessionState destState) {
		boolean result = false;
		// 如果可以转换至目标状态
		if (this.sessionState.verify(this.clientType, destState)) {
			this.sessionState = destState;
			result = true;
		}
		return result;
	}

	@Override
	public BaseSessionState getSessionState() {
		return sessionState;
	}

	@Override
	public void setSessionState(BaseSessionState baseSessionState) {
		this.sessionState = baseSessionState;

	}

	@Override
	public String getClientType() {
		return clientType;
	}

}
