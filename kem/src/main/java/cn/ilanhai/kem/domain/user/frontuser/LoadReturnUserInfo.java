package cn.ilanhai.kem.domain.user.frontuser;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class LoadReturnUserInfo extends AbstractEntity {

	private static final long serialVersionUID = -2312369805247687680L;
	/*
	 * 用户id
	 */
	private String userId;
	/*
	 * 用户名称
	 */
	private String userName;
	/*
	 * 用户电话
	 */
	private String userPhone;
	/*
	 * 用户邮箱
	 */
	private String userEmail;
	/*
	 * 用户状态
	 */
	private String status;
	/*
	 * 用户推广数
	 */
	private Integer extensionCount;
	
	/**
	 * 用户模板数
	 */
	private Integer templateCount;
	/**
	 * 会员状态：0：非会员，1：会员
	 */
	private Integer memberStatus;
	/**
	 * 是否即将过期：0：非会员或非即将过期，1：即将过期
	 */
	private Integer nearlyExpired;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getExtensionCount() {
		return extensionCount;
	}
	public void setExtensionCount(Integer extensionCount) {
		this.extensionCount = extensionCount;
	}
	public Integer getTemplateCount() {
		return templateCount;
	}
	public void setTemplateCount(Integer templateCount) {
		this.templateCount = templateCount;
	}
	public Integer getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(Integer memberStatus) {
		this.memberStatus = memberStatus;
	}
	public Integer getNearlyExpired() {
		return nearlyExpired;
	}
	public void setNearlyExpired(Integer nearlyExpired) {
		this.nearlyExpired = nearlyExpired;
	}

}
