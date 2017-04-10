package cn.ilanhai.kem.domain.material.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class SaveMeterialDto extends AbstractEntity {

	private static final long serialVersionUID = -611875836593634919L;

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
