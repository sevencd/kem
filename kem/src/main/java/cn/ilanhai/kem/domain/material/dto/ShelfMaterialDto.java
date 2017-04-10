package cn.ilanhai.kem.domain.material.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class ShelfMaterialDto extends AbstractEntity {
	private static final long serialVersionUID = 2540532546571031312L;

	private Integer materialState;
	private String materialId;

	public Integer getMaterialState() {
		return materialState;
	}
	public void setMaterialState(Integer materialState) {
		this.materialState = materialState;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

}
