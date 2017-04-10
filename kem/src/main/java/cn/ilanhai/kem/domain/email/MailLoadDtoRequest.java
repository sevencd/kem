package cn.ilanhai.kem.domain.email;

/**
 * 邮件加载请求
 * 
 * @author he
 *
 */
public class MailLoadDtoRequest {
	public MailLoadDtoRequest() {

	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	/**
	 * 邮件编号
	 */
	private String mailId;
}
