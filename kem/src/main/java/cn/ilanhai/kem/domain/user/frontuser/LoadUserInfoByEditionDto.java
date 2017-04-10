package cn.ilanhai.kem.domain.user.frontuser;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.MemberType;
import cn.ilanhai.kem.domain.enums.UserInfoType;
import cn.ilanhai.kem.domain.enums.UserStatus;
import cn.ilanhai.kem.domain.enums.UserType;
/**
 * 会员列表请求dto
 * @author csz
 * @time 2017-03-22 10:09
 */
public class LoadUserInfoByEditionDto extends AbstractEntity {

	private static final long serialVersionUID = -4387252467855093641L;
	/**
	 * 用户类型 {@link UserType}
	 */
	private Integer userType;
	/**
	 * 开始数
	 */
	private Integer startCount;
	/**
	 * 查看条数
	 */
	private Integer pageSize;
	/**
	 * 查询关键字
	 */
	private String keyWord;
	
	/**
	 * 是否主账号是否子账号
	 * {@link}UserRelationType
	 */
	private Integer relationType;
	/**
	 * 套餐服务id，-1试用用户，-2申请试用，null全部用户
	 */
	private Integer packageServiceId;
	/**
	 * 记录状态
	 * {@link UserStatus} 
	 */
	private Integer state=UserStatus.ENABLE.getValue();
	/**
	 * 名称 info_type
	 */
	private Integer companyInfoType=UserInfoType.COMPANY.getValue();
	/**
	 * 用户电话info_type
	 */
	private Integer emailInfoType=UserInfoType.EMAIL.getValue();
	/**
	 * 联系人info_type
	 */
	private Integer contactInfoType=UserInfoType.CONTACT.getValue();
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getStartCount() {
		return startCount;
	}

	public void setStartCount(Integer startCount) {
		this.startCount = startCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}



	public Integer getPackageServiceId() {
		return packageServiceId;
	}

	public void setPackageServiceId(Integer packageServiceId) {
		this.packageServiceId = packageServiceId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getCompanyInfoType() {
		return companyInfoType;
	}

	public void setCompanyInfoType(Integer companyInfoType) {
		this.companyInfoType = companyInfoType;
	}

	public Integer getEmailInfoType() {
		return emailInfoType;
	}

	public void setEmailInfoType(Integer emailInfoType) {
		this.emailInfoType = emailInfoType;
	}

	public Integer getContactInfoType() {
		return contactInfoType;
	}

	public void setContactInfoType(Integer contactInfoType) {
		this.contactInfoType = contactInfoType;
	}

	public Integer getRelationType() {
		return relationType;
	}

	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
	}


}
