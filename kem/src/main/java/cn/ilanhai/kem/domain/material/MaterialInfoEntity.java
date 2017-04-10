package cn.ilanhai.kem.domain.material;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class MaterialInfoEntity extends AbstractEntity {
	private static final long serialVersionUID = -8488920934758183071L;
	
	private Integer infoId;
	private String materialId;
	private Integer infoKey;
	private String infoValue;
	private Integer enable;
	public Integer getInfoId() {
		return infoId;
	}
	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}
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
	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}
}
