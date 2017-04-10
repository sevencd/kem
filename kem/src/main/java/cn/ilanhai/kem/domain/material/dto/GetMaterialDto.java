package cn.ilanhai.kem.domain.material.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class GetMaterialDto extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3347690937017827257L;
	private String materialId;

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
}
