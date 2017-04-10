package cn.ilanhai.kem.domain.material.classification;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class ClassificationRequest extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2550911976278910216L;

	private String[] categoryIds;
	private String[] materialIds;
	private String materialId;

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String[] getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(String[] categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String[] getMaterialIds() {
		return materialIds;
	}

	public void setMaterialIds(String[] materialIds) {
		this.materialIds = materialIds;
	}
}
