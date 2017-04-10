package cn.ilanhai.kem.domain.smsright.dto;

import java.util.Date;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchSmsResponseDto extends AbstractEntity {
	private static final long serialVersionUID = 5827289498662092060L;
	private String smsId;
	private String smsContent;
	private Date createtime;
	private Date sendtime;
	private Integer sendAmount;
	private Integer sendType;

	public String getSmsId() {
		return smsId;
	}

	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
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

	public Integer getSendType() {
		return sendType;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}
}
