package cn.ilanhai.kem.domain.email.dto;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchEmailResponseDto extends AbstractEntity {
	private static final long serialVersionUID = 5827289498662092060L;
	private String emailId;
	private String emailTitle;
	private Date createtime;
	private Date sendtime;
	private Integer sendAmount;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmailTitle() {
		return emailTitle;
	}

	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	public Integer getSendAmount() {
		return sendAmount;
	}

	public void setSendAmount(Integer sendAmount) {
		this.sendAmount = sendAmount;
	}
}
