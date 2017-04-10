package cn.ilanhai.kem.domain.user.backuser;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.UserStatus;
import cn.ilanhai.kem.domain.enums.UserType;

public class UserLoginResponseDto extends AbstractEntity {
	private static final long serialVersionUID = 1461284650108962945L;
	/**
	 * 用户ID
	 */
	private Integer userID;
	/**
	 * 用户类型
	 */
	private UserType userType;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 电话号码
	 */
	private String phoneNo;
	/**
	 * 邮件
	 */
	private String email;

	/**
	 * 状态
	 */
	private UserStatus status;

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}
}
