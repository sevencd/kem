package cn.ilanhai.kem.domain.midway;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class PullPublishedEntity extends AbstractEntity {

	private static final long serialVersionUID = -806707315605282821L;

	private String kid;
	private Integer relationType;//1 模板,2 专题,3 推广
	private Integer type;//1 PC，2 移动端

	public String getKid() {
		return kid;
	}

	public void setKid(String kid) {
		this.kid = kid;
	}

	public Integer getRelationType() {
		return relationType;
	}

	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
