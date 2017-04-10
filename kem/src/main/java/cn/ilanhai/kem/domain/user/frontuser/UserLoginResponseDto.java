package cn.ilanhai.kem.domain.user.frontuser;

import java.sql.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.UserStatus;
import cn.ilanhai.kem.domain.enums.UserType;

public class UserLoginResponseDto extends AbstractEntity {
	private static final long serialVersionUID = 1461284650108962945L;
	/**
	 * 用户ID
	 */
	private String userID;
	/**
	 * 用户类型
	 */
	private Integer userType;
	/**
	 * 用户名称
	 */
	private String username;
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

	private Boolean hasPwd;
	private String companyCode;
	/**
	 * 会员状态
	 */
	private String memberStatus;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Boolean getHasPwd() {
		return hasPwd;
	}

	public void setHasPwd(Boolean hasPwd) {
		this.hasPwd = hasPwd;
	}

	public String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
}
