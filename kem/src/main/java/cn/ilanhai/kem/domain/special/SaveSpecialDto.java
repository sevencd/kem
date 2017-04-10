package cn.ilanhai.kem.domain.special;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.ContextDataDto;

public class SaveSpecialDto extends AbstractEntity {
	private static final long serialVersionUID = 1184659505843486941L;
	private String specialId;
	private ContextDataDto data;
	private Integer pluginType;
	private Integer activeType;
	private Integer terminalType;

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
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
