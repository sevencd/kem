package cn.ilanhai.kem.domain.email;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryEmailContractDto extends AbstractEntity {
	public QueryEmailContractDto() {

	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String emailId;

}
