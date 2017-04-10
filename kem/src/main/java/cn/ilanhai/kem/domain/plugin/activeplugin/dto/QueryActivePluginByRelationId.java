package cn.ilanhai.kem.domain.plugin.activeplugin.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.ActivePluginType;

public class QueryActivePluginByRelationId extends AbstractEntity {

	private static final long serialVersionUID = -7270692734341127826L;
	// 关联ID
	private String relationId;

	private ActivePluginType activeType;
	
	private Integer isUsed;

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public ActivePluginType getActiveType() {
		return activeType;
	}

	public void setActiveType(ActivePluginType activeType) {
		this.activeType = activeType;
	}

	public Integer getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}
}
