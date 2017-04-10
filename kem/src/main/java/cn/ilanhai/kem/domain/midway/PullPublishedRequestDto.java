package cn.ilanhai.kem.domain.midway;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class PullPublishedRequestDto  extends AbstractEntity{

	private static final long serialVersionUID = -8850362187922891833L;
	private String relationId;

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	
}
