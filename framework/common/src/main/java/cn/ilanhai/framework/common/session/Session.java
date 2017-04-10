package cn.ilanhai.framework.common.session;

import java.io.Serializable;
import java.util.Map;

import cn.ilanhai.framework.common.exception.SessionContainerException;

/**
 * 
 * @author he
 *
 */
public interface Session extends Serializable {

	Object getParameter(String key) throws SessionContainerException;

	<T> T getParameter(String key, Class<T> t) throws SessionContainerException;

	Object setParameter(String key, Object value)
			throws SessionContainerException;

	void removeParameter(String key) throws SessionContainerException;

	boolean isExpire();

	Session fresh();

	void abandon() throws SessionContainerException;

	String getSessionId();

	/**
	 * 如果会话已过期，则返回false，否则，返回true,如果已过期则自动置为匿名会话
	 * 
	 * @return
	 */
	boolean checkTimeOut();

	/**
	 * 检查会话是否是指定状态 需要使用state的自身的verify方法，判断是否可以通过verify，
	 * 无法通过则返回false，否则返回true，并替换当前sessionState为传入sessionState
	 * 
	 * @param destState
	 * @return
	 */
	boolean verify(BaseSessionState destState);

	BaseSessionState getSessionState();

	void setSessionState(BaseSessionState baseSessionState);

	String getClientType();

}
