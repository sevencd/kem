package cn.ilanhai.kem.domain.rights.dto;

import cn.ilanhai.framework.app.domain.AbstractEntity;

public class QueryUnRightsLogDto extends AbstractEntity {
	private static final long serialVersionUID = 5676932240640775435L;
	private String manuscriptId;

	public String getManuscriptId() {
		return manuscriptId;
	}

	public void setManuscriptId(String manuscriptId) {
		this.manuscriptId = manuscriptId;
	}
}
