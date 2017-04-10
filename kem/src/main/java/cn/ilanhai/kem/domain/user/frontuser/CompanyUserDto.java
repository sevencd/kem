package cn.ilanhai.kem.domain.user.frontuser;

import java.util.Date;
import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.UserStatus;
import cn.ilanhai.kem.domain.enums.UserType;
/**
 * 
 * 添加企业用户dto
 * @time 2017-03-20 17:54
 * @author csz
 */
public class CompanyUserDto extends AbstractEntity{
	private static final long serialVersionUID = 1L;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 用户类型为前台用户
	 */
	private Integer userType=UserType.GENERAL_USER.getValue();
	/**
	 * 默认时间
	 */
	private Date createTime=new Date();
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 账号
	 */
	private String phoneNo;
	/**
	 * 密码
	 */
	private String loginPwd;
	/**
	 * 公司
	 */
	private String company;
	/**
	 * 所在公司编码
	 */
	private String companyCode;
	/**
	 * 职位
	 */
	private String job;
	/**
	 * 联系人
	 */
	private String contact;
	/**
	 * 联系电话
	 */
	private String contactPhoneNo;
	/**
	 * 用户状态
	 * @see UserStatus
	 */
	private Integer status;
	/**
	 * 购买版本ID
	 */
	private Integer packageServiceId;
	/**
	 * 用户信息
	 */
	private List<FrontUserInfoEntity> infos;
	
	
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
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
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
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<FrontUserInfoEntity> getInfos() {
		return infos;
	}
	public void setInfos(List<FrontUserInfoEntity> infos) {
		this.infos = infos;
	}
	public Integer getStatus() {
		return status;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getPackageServiceId() {
		return packageServiceId;
	}
	public void setPackageServiceId(Integer packageServiceId) {
		this.packageServiceId = packageServiceId;
	}
}
