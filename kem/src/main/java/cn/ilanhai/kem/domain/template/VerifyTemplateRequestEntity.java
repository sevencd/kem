package cn.ilanhai.kem.domain.template;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class VerifyTemplateRequestEntity extends AbstractEntity {
	private static final long serialVersionUID = 3184863553420126732L;
	private String templateId;
	private Boolean isOK;
	private String bouncedReason;
	private String tags;
	private String verifyName;

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Boolean getIsOK() {
		return isOK;
	}

	public void setIsOK(Boolean isOK) {
		this.isOK = isOK;
	}

	public String getBouncedReason() {
		return bouncedReason;
	}

	public void setBouncedReason(String bouncedReason) {
		this.bouncedReason = bouncedReason;
	}

	public String getVerifyName() {
		return verifyName;
	}

	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}
}
