package cn.ilanhai.kem.domain.email;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryMailInfoDto extends AbstractEntity {

	public QueryMailInfoDto() {

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
	/**
	 * 邮件id
	 */
	private String emailId;
}
