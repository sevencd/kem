package cn.ilanhai.kem.domain.material;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SearchMaterialEntity extends AbstractEntity {
	private static final long serialVersionUID = 2540532546571031312L;

	private Integer terminalType;
	private String materialName;
	private String userId;
	private Integer startCount;
	private Integer pageSize;
	private String materialId;
	private Integer materialState;
	private Integer materialType;
	
	private boolean isTrueSearch = false;

	public Integer getMaterialType() {
		return materialType;
	}

	public void setMaterialType(Integer materialType) {
		this.materialType = materialType;
	}

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public Integer getMaterialState() {
		return materialState;
	}

	public void setMaterialState(Integer materialState) {
		this.materialState = materialState;
	}

	public boolean isTrueSearch() {
		return isTrueSearch;
	}

	public void setTrueSearch(boolean isTrueSearch) {
		this.isTrueSearch = isTrueSearch;
	}
	
}
