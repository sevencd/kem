package cn.ilanhai.kem.event.args;

import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.event.DomainEventArgs;

/**
 * 前台用户登录事件
 * @author Nature
 *
 */
public class FrontUserLoginEvent extends DomainEventArgs{

	private static final long serialVersionUID = 9012678677549460187L;

	private String userId;//用户ID
	private String username;//用户名
	private UserType userType;//用户类型
	private String phoneNo;//电话号码
	private String email;//邮件
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
