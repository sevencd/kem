package cn.ilanhai.kem.domain.manuscript.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.ContextDataDto;

public class SaveManuscriptDto extends AbstractEntity {
	private static final long serialVersionUID = 1184659505843486941L;
	private String manuscriptId;
	private ContextDataDto data;
	private Integer pluginType;
	private Integer activeType;
	private Integer terminalType;

	public String getManuscriptId() {
		return manuscriptId;
	}

	public void setManuscriptId(String manuscriptId) {
		this.manuscriptId = manuscriptId;
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

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}
}
