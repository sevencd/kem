package cn.ilanhai.kem.domain.material.keyword;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class ModifyKeyWordRequest extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String keywords;
	private String materialId;
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getMaterialId() {
		return materialId;
	}
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
}
