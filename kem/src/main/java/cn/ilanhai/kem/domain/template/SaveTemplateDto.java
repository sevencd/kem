package cn.ilanhai.kem.domain.template;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.ContextDataDto;

public class SaveTemplateDto extends AbstractEntity {
	private static final long serialVersionUID = -1328284609471905821L;
	private String templateId;
	private Integer pluginType;
	private Integer activeType;
	private ContextDataDto data;
	private Integer terminalType;
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

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public ContextDataDto getData() {
		return data;
	}

	public void setData(ContextDataDto data) {
		this.data = data;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}
}
