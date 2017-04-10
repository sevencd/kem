package cn.ilanhai.kem.domain.user.frontuser;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.paymentservice.PayInfo.PayInfoResponseEntity;

/**
 * 用户信息
 * 
 * @author he
 *
 */
public class UserInfoDtoResponse extends AbstractEntity {
	private static final long serialVersionUID = -1358597851448072130L;
	private String username;
	private String phoneNo;
	private String email;

	private Integer UserType;

	private Boolean hasPwd;
	private String userId;

	private Integer userState;
	private String userPackageName;
	private Integer userPackageId;
	private PayInfoResponseEntity payInfo;

	/**
	 * 公司
	 */
	private String company;
	/**
	 * 公司电话
	 */
	private String companyPhoneNo;
	/**
	 * 公司地址
	 */
	private String companyAddress;
	/**
	 * 公司邮编
	 */
	private String zipCode;
	/**
	 * 公司联系人
	 * 
	 */
	private String contact;
	/**
	 * 联系人电话
	 */
	private String contactPhoneNo;

	private boolean userRelation;
	private boolean userTrial;

	public UserInfoDtoResponse() {

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

	public Integer getUserType() {
		return UserType;
	}

	public void setUserType(Integer userType) {
		UserType = userType;
	}

	public Boolean getHasPwd() {
		return hasPwd;
	}

	public void setHasPwd(Boolean hasPwd) {
		this.hasPwd = hasPwd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public boolean getUserRelation() {
		return userRelation;
	}

	public void setUserRelation(boolean userRelation) {
		this.userRelation = userRelation;
	}

	public String getCompanyPhoneNo() {
		return companyPhoneNo;
	}

	public void setCompanyPhoneNo(String companyPhoneNo) {
		this.companyPhoneNo = companyPhoneNo;
	}

	public String getContactPhoneNo() {
		return contactPhoneNo;
	}

	public void setContactPhoneNo(String contactPhoneNo) {
		this.contactPhoneNo = contactPhoneNo;
	}

	public Integer getUserState() {
		return userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}

	public String getUserPackageName() {
		return userPackageName;
	}

	public void setUserPackageName(String userPackageName) {
		this.userPackageName = userPackageName;
	}

	public Integer getUserPackageId() {
		return userPackageId;
	}

	public void setUserPackageId(Integer userPackageId) {
		this.userPackageId = userPackageId;
	}

	public PayInfoResponseEntity getPayInfo() {
		return payInfo;
	}

	public void setPayInfo(PayInfoResponseEntity payInfo) {
		this.payInfo = payInfo;
	}

	public boolean getUserTrial() {
		return userTrial;
	}

	public void setUserTrial(boolean userTrial) {
		this.userTrial = userTrial;
	}
}
