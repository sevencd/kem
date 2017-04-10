package cn.ilanhai.kem.domain.user.trialuser;

import java.util.Date;
import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.UserType;
import cn.ilanhai.kem.domain.user.frontuser.FrontUserInfoEntity;

/**
 * 
 * 申请试用dto
 * @time 2017-03-20 14:54
 * @author csz
 */
public class TrialUserDto extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户类型为普通用户
	 */
	private Integer userType=UserType.GENERAL_USER.getValue();
	/**
	 * 默认时间
	 */
	private Date createTime=new Date();
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 手机号
	 */
	private String phoneNo;
	/**
	 * 密码
	 */
	private String loginPwd;
	/**
	 * 所在公司
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
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户状态
	 */
	private Integer status;
	/*
	 * 用户信息
	 */
	private List<FrontUserInfoEntity> infos;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<FrontUserInfoEntity> getInfos() {
		return infos;
	}
	public void setInfos(List<FrontUserInfoEntity> infos) {
		this.infos = infos;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String getLoginPwd() {
		return loginPwd;
	}
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
}
