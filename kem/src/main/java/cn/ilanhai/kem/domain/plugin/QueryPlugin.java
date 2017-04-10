package cn.ilanhai.kem.domain.plugin;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.enums.PluginType;
import cn.ilanhai.kem.domain.enums.ManuscriptType;

public class QueryPlugin extends AbstractEntity {
	private static final long serialVersionUID = -7175549427983430975L;
	// 关联ID
	protected String relationId;
	// 关联ID类型，1 模板 2 专题 3 推广
	protected ManuscriptType relationType;

	protected PluginType pluginType;

	private Boolean isUsed;

	public QueryPlugin() {

	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public ManuscriptType getRelationType() {
		return relationType;
	}

	public void setRelationType(ManuscriptType relationType) {
		this.relationType = relationType;
	}

	public PluginType getPluginType() {
		return pluginType;
	}

	public void setPluginType(PluginType pluginType) {
		this.pluginType = pluginType;
	}

	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}
}
