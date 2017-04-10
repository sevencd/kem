package cn.ilanhai.kem.domain.template;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class LoadpublishinfoDto extends AbstractEntity {
	private static final long serialVersionUID = -6111061504536821065L;
	/**
	 * not null
	 */
	private String templateId;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
}
