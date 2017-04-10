package cn.ilanhai.kem.domain.user.frontuser;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 修改用户信息
 * 
 * @author he
 *
 */
public class UpdateUserInfoRequestDto extends AbstractEntity {

	private String username;
	private String email;
	private String phoneNo;
	
	/**
	 * 公司
	 */
	private String company;
	/**
	 * 联系人
	 */
	private String contact;
	/**
	 * 联系人电话
	 */
	private String contactPhoneNo;
	/**
	 * 邮编
	 */
	private String zipCode;
	/**
	 * 公司电话
	 */
	private String companyPhoneNo;
	/**
	 * 公司地址
	 */
	private String companyAddress;

	public UpdateUserInfoRequestDto() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactPhoneNo() {
		return contactPhoneNo;
	}

	public void setContactPhoneNo(String contactPhoneNo) {
		this.contactPhoneNo = contactPhoneNo;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCompanyPhoneNo() {
		return companyPhoneNo;
	}

	public void setCompanyPhoneNo(String companyPhoneNo) {
		this.companyPhoneNo = companyPhoneNo;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
}
