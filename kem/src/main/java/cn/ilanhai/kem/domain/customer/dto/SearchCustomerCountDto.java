package cn.ilanhai.kem.domain.customer.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchCustomerCountDto extends AbstractEntity {
	private static final long serialVersionUID = 1405006279198511375L;
	private String manuscriptId;
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getManuscriptId() {
		return manuscriptId;
	}

	public void setManuscriptId(String manuscriptId) {
		this.manuscriptId = manuscriptId;
	}
}
