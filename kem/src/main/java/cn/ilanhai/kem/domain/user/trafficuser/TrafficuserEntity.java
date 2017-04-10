package cn.ilanhai.kem.domain.user.trafficuser;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.TrafficuserType;

public class TrafficuserEntity extends AbstractEntity {
	private static final long serialVersionUID = 8642011467754685152L;
	private Integer trafficuserId;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 电话
	 */
	private String phoneNo;
	/**
	 * qq
	 */
	private String qqNo;
	/**
	 * 邮件
	 */
	private String email;
	/**
	 * 客户来源
	 */
	private TrafficuserType trafficuserType;
	/**
	 * 微信
	 */
	private String wechatNo;
	/**
	 * 职业
	 */
	private String vocation;
	/**
	 * 行业
	 */
	private String industry;
	/**
	 * 备注
	 */
	private String remark;

	private String extensionId;

	private Date createtime;

	private Integer trafficuserTypeCode;

	private String address;
	
	private Integer trafficfrom;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getTrafficuserTypeCode() {
		return trafficuserTypeCode;
	}

	public void setTrafficuserTypeCode(Integer trafficuserTypeCode) {
		this.trafficuserTypeCode = trafficuserTypeCode;
	}

	public Integer getTrafficuserId() {
		return trafficuserId;
	}

	public void setTrafficuserId(Integer trafficuserId) {
		this.trafficuserId = trafficuserId;
	}

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

	public String getQqNo() {
		return qqNo;
	}

	public void setQqNo(String qqNo) {
		this.qqNo = qqNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TrafficuserType getTrafficuserType() {
		return trafficuserType;
	}

	public void setTrafficuserType(TrafficuserType trafficuserType) {
		this.trafficuserType = trafficuserType;
	}

	public String getWechatNo() {
		return wechatNo;
	}

	public void setWechatNo(String wechatNo) {
		this.wechatNo = wechatNo;
	}

	public String getVocation() {
		return vocation;
	}

	public void setVocation(String vocation) {
		this.vocation = vocation;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getExtensionId() {
		return extensionId;
	}

	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getTrafficfrom() {
		return trafficfrom;
	}

	public void setTrafficfrom(Integer trafficfrom) {
		this.trafficfrom = trafficfrom;
	}

	
	
}
