package cn.ilanhai.kem.domain.template;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.ContextDataDto;

public class CopyOrPublishDto extends AbstractEntity {
	private static final long serialVersionUID = 314545896832008490L;
	private String templateId;
	private Boolean isEditor;
	private ContextDataDto data;
	private Integer pluginType;
	private Integer activeType;

	public CopyOrPublishDto() {

	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Boolean isEditor() {
		return isEditor;
	}

	public void setEditor(Boolean isEditor) {
		this.isEditor = isEditor;
	}

	public ContextDataDto getData() {
		return data;
	}

	public void setData(ContextDataDto data) {
		this.data = data;
	}

	public Integer getPluginType() {
		return pluginType;
	}

	public void setPluginType(Integer pluginType) {
		this.pluginType = pluginType;
	}

	public Integer getActiveType() {
		return activeType;
	}

	public void setActiveType(Integer activeType) {
		this.activeType = activeType;
	}
}
