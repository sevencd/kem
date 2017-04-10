package cn.ilanhai.kem.domain.material.dto;

import java.util.List;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class DeleteMeterialDto extends AbstractEntity {

	private static final long serialVersionUID = -611875836593634919L;

	private List<String> ids;

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

}
