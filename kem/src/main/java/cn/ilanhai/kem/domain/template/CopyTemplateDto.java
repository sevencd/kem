package cn.ilanhai.kem.domain.template;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class CopyTemplateDto extends AbstractEntity {
	private static final long serialVersionUID = -1811113794792337614L;
	private String templateId;
	private String templateName;

	public CopyTemplateDto() {

	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
}
