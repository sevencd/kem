package cn.ilanhai.kem.domain.material.remrek;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class RemrekRequest extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6121537437066637466L;

	private String materialId;
	private String remark;
	public String getMaterialId() {
		return materialId;
	}
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
