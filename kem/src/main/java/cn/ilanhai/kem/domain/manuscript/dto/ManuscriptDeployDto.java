package cn.ilanhai.kem.domain.manuscript.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.ContextDataDto;

public class ManuscriptDeployDto extends AbstractEntity {
	private static final long serialVersionUID = 372305567993130326L;
	private String manuscriptId;
	private ContextDataDto data;
	private Boolean isEditor;
	private Integer pluginType;
	private Integer activeType;

	public ManuscriptDeployDto() {

	}

	public ContextDataDto getData() {
		return data;
	}

	public void setData(ContextDataDto data) {
		this.data = data;
	}

	public String getManuscriptId() {
		return manuscriptId;
	}

	public void setManuscriptId(String manuscriptId) {
		this.manuscriptId = manuscriptId;
	}

	public Boolean isEditor() {
		return isEditor;
	}

	public void setIsEditor(Boolean isEditor) {
		this.isEditor = isEditor;
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
