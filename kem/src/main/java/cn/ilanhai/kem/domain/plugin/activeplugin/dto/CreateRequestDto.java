package cn.ilanhai.kem.domain.plugin.activeplugin.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class CreateRequestDto extends AbstractEntity{

	private static final long serialVersionUID = 5108037762979425240L;
	
	//模板或者专题ID
	private String relationId;
	// 固定值1模板 2专题
	private Integer relationType;
	//1：九宫格，2：刮刮卡
	private Integer activeType;
	
	public String getRelationId() {
		return relationId;
	}
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	
	public Integer getActiveType() {
		return activeType;
	}
	public void setActiveType(Integer activeType) {
		this.activeType = activeType;
	}
	public Integer getRelationType() {
		return relationType;
	}
	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
	}
	
}
