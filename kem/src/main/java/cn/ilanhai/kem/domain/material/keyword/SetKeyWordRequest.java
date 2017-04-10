package cn.ilanhai.kem.domain.material.keyword;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SetKeyWordRequest extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5881239336403640717L;
	
	private String keywords;
	private String[] materialIds;
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String[] getMaterialIds() {
		return materialIds;
	}
	public void setMaterialIds(String[] materialIds) {
		this.materialIds = materialIds;
	}

}
