package cn.ilanhai.kem.domain.customer;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class CustomerEntity extends AbstractEntity {
	private static final long serialVersionUID = -6687504826747471003L;
	private String customerId;
	private String name;
	private String phone;
	private String qq;
	private String email;
	private String address;
	private String originate;
	private String extensionName;
	/**
	 * 行业
	 */
	private String industry;
	/**
	 * 所在公司
	 */
	private String company;
	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 区
	 */
	private String area;
	/**
	 * qq签名
	 */
	private String qqAutograph;
	/**
	 * 签名
	 */
	private String autograph;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 专题带过来的标签
	 */
	private List<String> specialTag;
	/**
	 * 客户自己定义标签
	 */
	private List<String> customerTag;
	/**
	 * 客户分类
	 */
	private String type;
	/**
	 * 性别
	 */
	private String sex;

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getQqAutograph() {
		return qqAutograph;
	}

	public void setQqAutograph(String qqAutograph) {
		this.qqAutograph = qqAutograph;
	}

	public String getAutograph() {
		return autograph;
	}

	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<String> getSpecialTag() {
		return specialTag;
	}

	public void setSpecialTag(List<String> specialTag) {
		this.specialTag = specialTag;
	}

	public List<String> getCustomerTag() {
		return customerTag;
	}

	public void setCustomerTag(List<String> customerTag) {
		this.customerTag = customerTag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {

		this.type = type;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOriginate() {
		return originate;
	}

	public void setOriginate(String originate) {
		this.originate = originate;
	}

	public String getExtensionName() {
		return extensionName;
	}

	public void setExtensionName(String extensionName) {
		this.extensionName = extensionName;
	}
}
