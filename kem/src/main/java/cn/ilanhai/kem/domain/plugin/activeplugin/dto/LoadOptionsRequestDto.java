package cn.ilanhai.kem.domain.plugin.activeplugin.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class LoadOptionsRequestDto extends AbstractEntity {

	private static final long serialVersionUID = 1108003959180568311L;

	private String relationId;
	private Integer optionNum;
	private Integer activePluginType;
	private Integer optionNumMin;
	private Boolean isEven;
	
	public Integer getOptionNumMin() {
		return optionNumMin;
	}

	public void setOptionNumMin(Integer optionNumMin) {
		this.optionNumMin = optionNumMin;
	}

	public Boolean getIsEven() {
		return isEven;
	}

	public void setIsEven(Boolean isEven) {
		this.isEven = isEven;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public Integer getOptionNum() {
		return optionNum;
	}

	public void setOptionNum(Integer optionNum) {
		this.optionNum = optionNum;
	}

	public Integer getActivePluginType() {
		return activePluginType;
	}

	public void setActivePluginType(Integer activePluginType) {
		this.activePluginType = activePluginType;
	}

}
