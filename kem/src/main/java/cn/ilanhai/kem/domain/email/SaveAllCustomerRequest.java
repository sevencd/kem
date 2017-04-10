package cn.ilanhai.kem.domain.email;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 
 * @author DGJ
 *
 */
public class SaveAllCustomerRequest extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3695538562016466165L;

	/**
	 * 邮件ID，不可为空
	 */
	private String emailId;
	private String searchStr;
	private String originate;
	private Integer startCount;
	private Integer pageSize;
	private String userId;
	/**
	 * 客户分组
	 */
	private Integer type;
	/**
	 * 是否为发送邮件或短信 ，0 都不是，1 发送短信，2 发送邮件
	 */
	private Integer sendType;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
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

	public String getSearchStr() {
		return searchStr;
	}

	public void setSearchStr(String searchStr) {
		this.searchStr = searchStr;
	}

	public String getOriginate() {
		return originate;
	}

	public void setOriginate(String originate) {
		this.originate = originate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSendType() {
		return sendType;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

}
