package cn.ilanhai.framework.common.session;

import cn.ilanhai.framework.common.session.enums.ClientTypes;
import cn.ilanhai.framework.common.session.enums.SessionStateType;
 
/**
 * 后台用户已登录状态
 * 
 * @author Nature
 *
 */
public class BackUserLoginedState extends BaseSessionState {

	private SessionStateType sessionStateType = SessionStateType.BACK_USER_LOGINED_STATE;

	public SessionStateType getSessionStateType() {
		return sessionStateType;
	}

	@Override
	public boolean verify(String clientType, BaseSessionState currentState) {
		// 仅制作系统中的用户可以达到该状态
		if (!clientType.equals(ClientTypes.BackManageSys.toString())) {
			return false;
		}
		// 仅可以从匿名状态达到该状态
		if (!(currentState instanceof AnonymousSessionState)) {
			return false;
		}
		return true;
	}

}
