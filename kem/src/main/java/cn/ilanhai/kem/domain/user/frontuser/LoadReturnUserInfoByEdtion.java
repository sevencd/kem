package cn.ilanhai.kem.domain.user.frontuser;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.MemberType;
/**
 * 会员列表返回至前端的dto
 * @author csz
 * @time 2017-03-22 10:09
 */
public class LoadReturnUserInfoByEdtion extends AbstractEntity {

	private static final long serialVersionUID = -2312369805247687680L;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 会员套餐id
	 */
	private String packageServiceId;
	/**
	 * 用户名称
	 */
	private String company;
	/**
	 * 用户电话
	 */
	private String userPhone;
	/**
	 * 联系人
	 */
	private String contact;
	/**
	 * 会员类型  账号等级
	 * @see MemberType
	 */
	private String memberType;
	/**
	 * 用户邮箱
	 */
	private String userEmail;
	/**
	 * 用户状态
	 */
	private Integer status;
	/**
	 * 是否主账号是否子账号
	 * {@link}UserRelationType
	 */
	private Integer relationType;
	/**
	 * 设计师的上架作品数量
	 */
	private Integer templateCount;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	public String getPackageServiceId() {
		return packageServiceId;
	}
	public void setPackageServiceId(String packageServiceId) {
		this.packageServiceId = packageServiceId;
	}
	public Integer getRelationType() {
		return relationType;
	}
	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
	}
	public Integer getTemplateCount() {
		return templateCount;
	}
	public void setTemplateCount(Integer templateCount) {
		this.templateCount = templateCount;
	}
}
