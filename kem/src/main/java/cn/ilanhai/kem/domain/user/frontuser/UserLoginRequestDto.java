package cn.ilanhai.kem.domain.user.frontuser;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class UserLoginRequestDto extends AbstractEntity{

	private static final long serialVersionUID = -1548404132841460699L;

	/**
	 * 登录标识，目前为电话号码
	 */
	private String phoneNo;
	/**
	 * 登录密码
	 */
	private String loginPwd;
	
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getLoginPwd() {
		return loginPwd;
	}
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	
}
