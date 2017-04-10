package cn.ilanhai.kem.domain.email;

public class MailSendRequest {
	public MailSendRequest() {

	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Integer getSendType() {
		return sendType;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

	public String getEmailTitle() {
		return emailTitle;
	}

	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	/**
	 * 邮件ID，不可为空
	 */
	private String emailId;

	/**
	 * 发送类型：1，正式发送；2，测试发送，不可为空
	 */
	private Integer sendType;

	/**
	 * 邮件标题，仅测试发送时生效，测试发送时不可为空
	 */
	private String emailTitle;

	/**
	 * 发件人名称，仅测试发送时生效，测试发送时不可为空
	 */
	private String fromName;

	/**
	 * 
	 */
	private String toEmail;// 收件人地址，仅测试发送时生效，测试发送时不可为空
}
