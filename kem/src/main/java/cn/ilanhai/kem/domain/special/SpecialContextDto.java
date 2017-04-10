package cn.ilanhai.kem.domain.special;

import cn.ilanhai.framework.app.domain.AbstractEntity;
import cn.ilanhai.kem.domain.ContextDataDto;

public class SpecialContextDto extends AbstractEntity {
	private static final long serialVersionUID = -1328284609471905821L;
	private String specialId;
	private ContextDataDto data;

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}

	public ContextDataDto getData() {
		return data;
	}

	public void setData(ContextDataDto data) {
		this.data = data;
	}
}
