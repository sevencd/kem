package cn.ilanhai.kem.domain.material.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchMaterialDto extends AbstractEntity {
	private static final long serialVersionUID = 2540532546571031312L;

	private Integer terminalType;
	private String materialName;
	private Integer materialState;
	private Integer startCount;
	private Integer pageSize;
	private Integer materialType;

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public Integer getStartCount() {
		return startCount;
	}

	public void setStartCount(Integer startCount) {
		this.startCount = startCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getMaterialState() {
		return materialState;
	}

	public void setMaterialState(Integer materialState) {
		this.materialState = materialState;
	}

	public Integer getMaterialType() {
		return materialType;
	}

	public void setMaterialType(Integer materialType) {
		this.materialType = materialType;
	}
}
