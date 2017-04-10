package cn.ilanhai.kem.domain.user.trafficuser;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryTrafficUserTypeEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9051747708762591244L;

	private Integer type;
	private String relationId;

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
