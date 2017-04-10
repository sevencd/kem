package cn.ilanhai.kem.domain.rights.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryUnRightsTimesDto extends AbstractEntity {

	private static final long serialVersionUID = -1718288463854965210L;
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
