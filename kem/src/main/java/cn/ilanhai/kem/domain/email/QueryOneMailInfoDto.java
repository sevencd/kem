package cn.ilanhai.kem.domain.email;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryOneMailInfoDto extends AbstractEntity {

	public QueryOneMailInfoDto() {

	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 邮件id
	 */
	private String emailId;
	private String key;

}
