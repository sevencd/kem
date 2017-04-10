package cn.ilanhai.framework.common.session;



import cn.ilanhai.framework.common.exception.SessionContainerException;
import cn.ilanhai.framework.common.session.enums.SessionStateType;

public class RedisSession extends AbstractSession {

	// 用户状态
	public static final String STATE_ANONYMOUS = "anonymous_state";
	public static final String STATE_FRONTUSER_LOGINED = "frontuser_logined_state";
	public static final String STATE_BACKUSER_LOGINED = "backuser_logined_state";
	/**
	 * 会话状态
	 */
	private BaseSessionState sessionState;
	/**
	 * 会话客户端类型，依据约定而定，可空
	 */
	private String clientType;

	public RedisSession(SessionManager sessionManager, BaseSessionState state,
			String clientType) {
		super(sessionManager);
		this.sessionState = state;
		this.clientType = clientType;
	}

	public Object getParameter(String key) throws SessionContainerException {
		if (key == null || key.length() <= 0)
			return null;
		if (this.sessionManager == null)
			return null;
		if (!(this.sessionManager instanceof RedisSessionManagerImpl))
			return null;
		return ((RedisSessionManagerImpl) this.sessionManager)
				.loadSessionParameter(this, key);
	}

	public <T> T getParameter(String key, Class<T> t)
			throws SessionContainerException {
		Object object = null;
		object = this.getParameter(key);
		if (object == null)
			return null;
		if (object.getClass() != t)
			return null;
		return (T) object;
	}

	public Object setParameter(String key, Object value)
			throws SessionContainerException {
		if (key == null || key.length() <= 0)
			return null;
		if (value == null)
			return null;
		if (this.sessionManager == null)
			return null;
		if (!(this.sessionManager instanceof RedisSessionManagerImpl))
			return null;
		if (!((RedisSessionManagerImpl) this.sessionManager)
				.saveSessionParameter(this, key, value))
			return null;
		return value;
	}

	public void removeParameter(String key) throws SessionContainerException {
		if (key == null || key.length() <= 0)
			return;
		if (this.sessionManager == null)
			return;
		if (!(this.sessionManager instanceof RedisSessionManagerImpl))
			return;
		((RedisSessionManagerImpl) this.sessionManager).deleteSessionParameter(
				this, key);
	}

	public void abandon() throws SessionContainerException {
		if (this.sessionManager == null)
			return;
		this.sessionManager.unRegister(this);
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
