package cn.ilanhai.kem.domain.material.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryMaterialInfoDto extends AbstractEntity {
	private static final long serialVersionUID = -8488920934758183071L;

	private String materialId;
	private Integer infoKey;
	private String infoValue;

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public Integer getInfoKey() {
		return infoKey;
	}

	public void setInfoKey(Integer infoKey) {
		this.infoKey = infoKey;
	}

	public String getInfoValue() {
		return infoValue;
	}

	public void setInfoValue(String infoValue) {
		this.infoValue = infoValue;
	}
}
