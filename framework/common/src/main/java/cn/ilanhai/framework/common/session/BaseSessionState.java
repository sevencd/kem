package cn.ilanhai.framework.common.session;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import cn.ilanhai.framework.common.session.enums.SessionStateType;

public abstract class BaseSessionState implements Serializable {
 
	protected Date deadline;

	public BaseSessionState() {
		deadline = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(deadline);
		cal.add(Calendar.MINUTE, 30);

		deadline = cal.getTime();

	}

	/**
	 * session状态的名称，不可重名
	 */
	public abstract SessionStateType getSessionStateType();

	/**
	 * 判断是否可以改变为当前状态
	 * 
	 * @param clientType
	 *            客户端类型
	 * @param currentState
	 *            当前的状态
	 * @return
	 */
	public abstract boolean verify(String clientType,
			BaseSessionState currentState);

	/**
	 * 是否已过期
	 * 
	 * @return
	 */
	public boolean isTimeout() {
		Date now = new Date();
		if (now.after(deadline)) {
			return true;
		} else {
			return false;
		}
	}

	public void fresh() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(deadline);
		cal.add(Calendar.MINUTE, 30);

		deadline = cal.getTime();
	}
}
