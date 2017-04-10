package cn.ilanhai.kem.domain.customer.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchCustomerDto extends AbstractEntity {
	private static final long serialVersionUID = 1405006279198511375L;
	private String searchStr;
	private String originate;
	private Integer startCount;
	private Integer pageSize;
	private Integer count;
	private String userId;
	/**
	 * 客户分组
	 */
	private Integer type;
	/**
	 * 是否为发送邮件或短信 ，0 都不是，1 发送短信，2 发送邮件
	 */
	private Integer sendType;

	public Integer getSendType() {
		return sendType;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
