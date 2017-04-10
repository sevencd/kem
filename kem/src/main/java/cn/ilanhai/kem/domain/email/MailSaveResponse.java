package cn.ilanhai.kem.domain.email;

import cn.ilanhai.framework.app.domain.AbstractEntity;

/**
 * 
 * @author he
 *
 */
public class MailSaveResponse  extends AbstractEntity{
	public MailSaveResponse() {

	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	private String emailId;
}
