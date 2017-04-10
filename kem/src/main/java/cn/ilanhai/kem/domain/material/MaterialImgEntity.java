package cn.ilanhai.kem.domain.material;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class MaterialImgEntity extends AbstractEntity {
	private static final long serialVersionUID = 7742362826517335504L;
	
	private String material_id;
	private String img_id;
	public String getMaterial_id() {
		return material_id;
	}
	
	public void setMaterial_id(String material_id) {
		this.material_id = material_id;
	}
	public String getImg_id() {
		return img_id;
	}
	public void setImg_id(String img_id) {
		this.img_id = img_id;
	}
}
