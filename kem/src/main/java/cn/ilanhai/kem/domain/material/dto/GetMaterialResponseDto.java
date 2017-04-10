package cn.ilanhai.kem.domain.material.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class GetMaterialResponseDto extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3347690937017827257L;
	private String thumbnail;
	private String materialType;

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
}
