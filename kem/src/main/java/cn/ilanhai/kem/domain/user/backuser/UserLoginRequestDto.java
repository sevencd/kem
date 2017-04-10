package cn.ilanhai.kem.domain.user.backuser;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class UserLoginRequestDto extends AbstractEntity {

	private static final long serialVersionUID = -1548404132841460699L;

	/**
	 * 登录用户名
	 */
	private String userName;
	/**
	 * 登录密码
	 */
	private String loginPwd;

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
